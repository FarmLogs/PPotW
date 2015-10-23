def eat(a, char)
  {'['=>a+7,']'=>a-7,'('=>a*41,')'=>a/41,'{'=>2**a,'}'=>Math.log2(a)}[char]
end

def wow(r)
  ->s{s.gsub(/\(|\)|\{|\}|\[|\]/,'').empty? ? s.split(//).inject(s.length){|accumulator, char| eat(accumulator, char)} == s.length : false}\
    [r.gsub(/('.*?')/,"")]
end

puts "true: #{wow("()()()([')'][])")}"
puts "false: #{wow("([)]")}"
puts "true: #{wow("()")}"
puts "true: #{wow("[]")}"
puts "true: #{wow("{}")}"
puts "false: #{wow("'(((()()')))")}"
puts "false: #{wow("'asdfa' dfasjfdsa 'fdafdas'")}"
puts "true: #{wow("'(((((('()([][]']')")}"
