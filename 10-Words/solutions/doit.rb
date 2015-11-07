require 'set'

dict = Set.new
File.open("dict.txt") do |f|
  f.each_line do |word|
    dict.add(word)
  end
end


