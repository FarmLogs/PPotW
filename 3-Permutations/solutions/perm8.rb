require 'json'

def perm8?(num)
  print "#{num} is "
    if num.size < 3
        if num.split('').permutation.map(&:join).any?{|x| x.to_i % 8 == 0 }
            puts "Valid"
        else
            puts "Invalid"
        end
    else
        if num.split('').permutation(3).map(&:join).any?{|x| x.to_i % 8 == 0}
           puts "Valid"
        else
           puts "Invalid"
        end
    end
end

values = JSON.parse(File.read('data.json'))
values.map {|num| perm8? num}
