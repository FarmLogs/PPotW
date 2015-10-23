def eat(a, char)
  {'['=>a+7,']'=>a-7,'('=>a*41,')'=>a/41,'{'=>2**a,'}'=>Math.log2(a)}[char]
end

def wow(r)
  ->s{s.gsub(/\(|\)|\{|\}|\[|\]/,'').empty? ? s.split(//).reduce(s.length){|accumulator, char| eat(accumulator, char)} == s.length : false}\
    [r.gsub(/('.*?')/,"")]
end

puts wow("()()()([')'][])")
puts wow("([)]")
puts wow("()")
puts wow("[]")
puts wow("{}")
puts wow("'(((()()')))")
puts wow("'asdfa' dfasjfdsa 'fdafdas'")
puts wow("'(((((('()([][]']')")
