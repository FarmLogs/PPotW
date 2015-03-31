function fizzBuzz(){
    possibleVals = ['','Fizz', 'Buzz', 'FizzBuzz'];
    fizzyCount = [1,0,0];
    buzzyCount = [2,0,0,0,0];
    for (var i = 1; i <= 100; i++){
        possibleVals[0] = i;
        console.log(possibleVals[fizzyCount[i%3]+buzzyCount[i%5]]);
    }
}

fizzBuzz();
