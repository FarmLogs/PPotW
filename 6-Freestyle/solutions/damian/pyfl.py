#!/usr/bin/env python3

"""
Python library for the FarmLogs REST API.
Example:
    >>> fl = FarmLogs('wdrvi@example.com', '***top_secret***')
    >>> from pprint import pprint as pp
    >>> pp(fl['fields'])
"""

import json
import requests
from urllib.parse import urljoin

TABLES_NAMES = {
  'activities':      'activity',
  'chemicals':       'chemical',
  'chemical_types':  'chemical_type',
  'commodities':     'commodity',
  'crops':           'crop',
  'enterprises':     'enterprise',
  'fertilizers':     'fertilizer',
  'fields':          'field',
  'field_groups':    'field_group',
  'equipment':       'equipment',
  'implement_types': 'implement_type',
  'notes':           'note',
  'people':          'person',
  'tractors':        'tractor',
  'varieties':       'variety',
  'field_shares':    'field_share',
  'aat_recordings':  'recording',
  'aat_frames':      'frame',
  'activity_types':  'activity_type',
  'field_layers':    'field_layer'
}

TABLES = list(TABLES_NAMES.keys())
TABLES_KEYS = {table: name + '_id' for table, name in TABLES_NAMES.items()}
KEYS_TABLES = {key: table for table, key in TABLES_KEYS.items()}
KEYS_NAMES = {key: TABLES_NAMES[table] for table, key in TABLES_KEYS.items()}

class FarmLogs(dict):
  RAW_ENDPOINT = "https://{}.farmlogs.com/{}/"
  AUTH_ENDPOINT = "https://{}.farmlogs.com/users/login"
  SYNC_ENDPOINT = "https://{}.farmlogs.com/{}/synchronize/"
  API_ENDPOINT = "https://{}.farmlogs.com/{}/api/"
  HEADERS = {
    'X-Requested-With': 'XMLHttpRequest',
    'User-Agent': 'Android-Farmlogs/3.11.0'
  }
  JSON_HEADERS = {
    'X-Requested-With': 'XMLHttpRequest',
    'Content-Type': 'application/json; charset=utf-8',
    'User-Agent': 'Android-Farmlogs/3.11.0'
  }

  def __init__(self, email, password, **kwargs):
    dict.__init__(self)
    release = kwargs.get('release', 'stage')
    version = kwargs.get('version', 'v1.5')
    self.raw_endpoint = FarmLogs.RAW_ENDPOINT.format(release, version)
    self.endpoint = FarmLogs.API_ENDPOINT.format(release, version)
    self.sync_endpoint = FarmLogs.SYNC_ENDPOINT.format(release, version)
    self._sess = requests.session()

    auth = FarmLogs.AUTH_ENDPOINT.format(release)
    auth_data = {'email': email, 'password': password}
    self.session = self._sess.post(auth, headers=FarmLogs.HEADERS, data=auth_data).json()
    self.sync()

  def get(self, table, id_=None):
    r = self._get(None, table, id_).json()
    if isinstance(r, list): return ResultList(r, table, self)
    else: return Model(r, table, self)

  def sync(self):
    request = self._sync_request()
    r = self._sess.post(self.sync_endpoint, headers=FarmLogs.JSON_HEADERS, data=json.dumps(request)).json()
    self.update({table: ResultList(r[table]['data'], table, self) for table in r.keys() if r[table]['data']})
    for item in (item for collection in self.values() for item in collection):
      item._include()
    return self

  def _sync_request(self):
    return {table: {'model': table} for table in TABLES}

  def _get(self, params, *fragments, **kwargs):
    url = FarmLogs._append_url(self._get_endpoint(kwargs.get('raw', False)), *fragments)
    return self._sess.get(url, headers=FarmLogs.HEADERS, params=params)

  def _put(self, data, *fragments, **kwargs):
    url = FarmLogs._append_url(self._get_endpoint(kwargs.get('raw', False)), *fragments)
    return self._sess.put(url, headers=FarmLogs.JSON_HEADERS, data=json.dumps(data))

  def _post(self, data, *fragments, **kwargs):
    url = FarmLogs._append_url(self._get_endpoint(kwargs.get('raw', False)), *fragments)
    return self._sess.post(url, headers=FarmLogs.JSON_HEADERS, data=json.dumps(data))

  def _get_endpoint(self, raw):
    return {True: self.raw_endpoint, False: self.endpoint}[raw]

  @staticmethod
  def _append_url(base, *fragments):
    for fragment in (f for f in fragments if f):
      base = urljoin(base, str(fragment)) + '/'
    return base.strip('/')


class ResultList(list):
  def __init__(self, source, table, fl):
    list.__init__(self, (Model(i, table, fl) for i in source))
    self.table = table
    self._fl = fl

  def save(self):
    for model in self:
      model.save()

  def get(self, id_):
    results = [model for model in self if model['id'] == id_]
    if not results: return None
    return results[0]

class Model(dict):

  def __init__(self, source, table, fl):
    dict.__init__(self, source)
    self.table = table
    self._fl = fl

  def _include(self):
    for key, id_ in ((key, value) for key, value in self.items() if key in KEYS_TABLES.keys()):
      name = KEYS_NAMES[key]
      table = KEYS_TABLES[key]
      if table not in self._fl.keys(): continue
      setattr(self, name, self._fl[table].get(id_))

  def setref(self, foreign):
    id_key = TABLES_KEYS(foreign.table, None)
    if not id_key:
      raise TypeError('{} cannot be referenced here'.format(foreign.table))
    self[id_key] = foreign['id']
    setattr(self, TABLES_NAMES[foreign.table], foreign)

  def save(self):
    if 'id' in self.keys():
      r = self._fl._put(self, self.table, self['id'])
    else:
      r = self._fl._post(self, self.table)
    self.update(r.json())
    return self
