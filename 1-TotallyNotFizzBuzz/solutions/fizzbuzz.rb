for i in 1..100
 unless not ((i % 3 == 0) and (i % 5 == 0))
  puts "fizzbuzz"
 else
  unless not (i % 3 == 0)
   puts "fizz"
  else
   unless not (i % 5 == 0)
    puts "buzz"
   else
    puts i
   end
  end
 end
end
