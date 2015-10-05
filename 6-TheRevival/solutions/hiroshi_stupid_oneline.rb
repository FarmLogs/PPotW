n = 5; (0..(n-1)).each{|x| (0..(n-1)).each{|y| print((x==y or x+y==n-1)?'x':' ') };puts ''}
