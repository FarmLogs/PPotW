(doseq [i (range 1, 101)]
    (let [f ({0 "Fizz"} (mod i 3))
          b ({0 "Buzz"} (mod i 5))
          s  ({"" i} (str f b))]
      (println (str f b s))))
