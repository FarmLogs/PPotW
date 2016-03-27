import time
import threading
from threading import Thread

def fizz():
	nums = sorted(list(set(range(0,101,3)).difference(set(range(0,101,15)))))
	time.sleep(3)
	print 'Fizz'
	for i in range(1, len(nums)):
		time.sleep(nums[i] - nums[i-1])
		print 'Fizz'

def buzz():
	nums = sorted(list(set(range(0,101,5)).difference(set(range(0,101,15)))))
	time.sleep(5)
	print 'Buzz'
	for i in range(1, len(nums)):
		time.sleep(nums[i] - nums[i-1])
		print 'Buzz'


def fizzbuzz():
	nums = sorted(list(set(range(0,101,3)).intersection(range(0,101,5))))
	print 'FizzBuzz'
	for i in range(len(nums) - 1):
		time.sleep(15)
		print 'FizzBuzz'

def num():
	nums = set(range(100))
	nums = nums.difference(set(range(0,101,3)))
	nums = sorted(list(nums.difference(set(range(0,101,5)))))
	time.sleep(1)
	print nums[0]
	for i in range(1, len(nums)):
		time.sleep(nums[i] - nums[i-1])
		print nums[i]

		
if __name__ == '__main__':
	Thread(target = fizz).start()
	Thread(target = buzz).start()
	Thread(target = fizzbuzz).start()
	Thread(target = num).start()

