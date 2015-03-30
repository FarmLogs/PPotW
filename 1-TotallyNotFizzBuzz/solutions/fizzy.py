mod_three = {
    '0': "Fizz"
}

mod_five = {
    '0': "Buzz"
}

for i in range(100):
    fizz = mod_three.get(str(i % 3), "")
    buzz = mod_five.get(str(i % 5), "")
    print fizz + buzz or i


