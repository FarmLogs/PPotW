def clean!(exp)
  exp.gsub(/('.*?')/,"")
end

def cleaned?(exp)
  exp.gsub(/\(|\)|\{|\}|\[|\]/,'').empty?
end

def eat(a, char)
  case char
  when '['
    a+7
  when ']'
    a-7
  when '('
    a * 41
  when ')'
    a / 41
  when '{'
    2 ** a
  when '}'
    Math.log2(a)
  end
end

def wow(r)
  s = clean!(r)
  return cleaned?(s) ? s.split(//).reduce(1){|accumulator, char| eat(accumulator, char)} == 1 : false
end

puts wow("()()()([')'][])")
puts wow("'(((()()')))")
puts wow("'asdfa' dfasjfdsa 'fdafdas'")
puts wow("'(((((('()([][]']')")
