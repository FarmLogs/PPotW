var count = 0;
var test;
function returnSelf() {
  count ++;
  if (count == 1) {
    return returnSelf.toString();
  }
}

console.log(returnSelf());