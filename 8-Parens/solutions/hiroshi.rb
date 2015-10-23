def eat(a, char)
  {91=>a+7,93=>a-7,40=>a*41,41=>a/41,123=>2**a,125=>Math.log2(a)}[char]
end
def wow(r)
  ->s{!s.gsub(/#{[40,124,41,124,123,124,125,124,91,124,93].pack('c'*11)}/,String.new)[/./] ? s.unpack('c'*s.length).inject(s.length){|accumulator, char| eat(accumulator, char)} == s.length : false}\
    [r.gsub(/#{[39,46,42,63,39].pack('C'*5)}/,String.new)]
end

puts "true: #{wow("()()()([')'][])")}"
puts "false: #{wow("([)]")}"
puts "true: #{wow("()")}"
puts "true: #{wow("[]")}"
puts "true: #{wow("{}")}"
puts "false: #{wow("'(((()()')))")}"
puts "false: #{wow("'asdfa' dfasjfdsa 'fdafdas'")}"
puts "true: #{wow("'(((((('()([][]']')")}"
