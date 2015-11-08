require 'set'

dict = Set.new
File.open("dict.txt") do |f|
  f.each_line do |word|
    dict.add(word)
  end
end

def find_word(charseq):
  for i in 0..charset.size-1 do
    potential_word = charseq[0..i]
    if dict.include? potential_word
      return potential_word, charseq[i+1..-1]
  end
  return nil
end

def find_all_words(input_str)
  found_words = Set.new
  input_str.split('').permutations{|p|
    temp_sets = []
    iterator = p.join
    while (found = find_word(iterator))
      temp_sets.push(found.first)
      iterator = found.second
    end
    temp_sets.reduce([]){|c, n|
      found_words.add(Set.new())
    }
  }
end
