
numbers = [23,4,66,78,99,45,73,87,96,32]
def my_bubble_sort(data):
    for i in range (0,len(numbers)-1):
        for b in range (i+1,len(numbers)-1):
            #print "before:"
            #print numbers[i],numbers[b]
            if numbers[i]>numbers[b]:
                #print "switch"
                temp = numbers[i]
                numbers[i] = numbers [b]
                numbers[b] = temp
            print "after:"
            print numbers[i],numbers[b]

    for num in numbers:
        print num

def bubble(data):
    print 'Original:',data
    for i in range (len(data)-1):
        for j in range(len(data)-1):
            if (data[j]>data[j+1]):
                temp = data[j]
                data[j] = data [j+1]
                data[j+1] = temp
        print 'Rount %d: '%i,data

bubble (numbers)
