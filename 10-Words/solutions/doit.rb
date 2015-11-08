require 'set'

@dict = Set.new
File.open("dict.txt") do |f|
  f.each_line do |word|
    @dict.add(word.strip)
  end
end

def find_word(charseq)
  for i in 0..charseq.size-1 do
    potential_word = charseq[0..i]
    if @dict.include? potential_word
      return potential_word, charseq[i+1..-1]
    end
  end
  nil
end

def count_combo_length(set_of_sets)
  set_of_sets.to_a.reduce(0){|sum, s| sum + s.to_a.reduce(0){|inner_sum, str| inner_sum+str.size}}
end

def find_all_words(input_str)
  found_words = Set.new
  input_str.split('').permutation{|p|
    temp_sets = []
    iterator = p.join
    while (found = find_word(iterator))
      temp_sets.push(found[0])
      iterator = found[1]
    end
    temp_sets.reduce([]){|c, n|
      s = c+[n]
      found_words.add(Set.new(s))
      s
    }
  }
  found_words
end

def solve(input_str, direct: false)
  all_words = find_all_words(input_str)
  set_length = count_combo_length(all_words)
  if direct
    return all_words
  end
  set_length
end

@max = 0
"abcdefghijklmnopqrstuvwxyz".split('').repeated_combination(5){|p|
  str_to_solve = p.join
  if (c = solve(str_to_solve)) > @max
    @max = c
    @max_str = str_to_solve
  end
}
puts "Maximum charset is #{@max} with combination #{@max_str}"
puts solve(@max_str, direct: true).to_a.map{|s| s.to_a.join ', '}
