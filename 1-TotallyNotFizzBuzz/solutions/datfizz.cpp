#include <iostream>
#include <string>
#include <map>
#include <algorithm>

using namespace std;

map<int, string> init_dataz() {
	map<int, string> mappings;
	for (int i = 1; i <= 100; i++) {
		mappings[i] = to_string(i);
	}

	for (int i = 3; i <= 100; i += 3) {
		mappings[i] = "Fizz"; 
	}

	for (int i = 5; i <= 100; i += 5) {
		mappings[i] = "Buzz"; 
	}

	for (int i = 15; i <= 100; i += 15) {
		mappings[i] = "FizzBuzz";
	}

	return mappings;
}

int main(int argc, char * argv[]) {
	const auto dataz = init_dataz();
	for_each(dataz.begin(), dataz.end(), [](const pair<int, string> & data){ cout << data.second << endl; });
	return 0;
}
