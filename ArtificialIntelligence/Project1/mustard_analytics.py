#!/usr/bin/python
import sys
import csv
import datetime

  
# COMPSCI 383 Homework 0 
#  
# Fill in the bodies of the missing functions as specified by the comments and docstrings.


# Exercise 0. (8 points)
#  
def read_data(file_name):
    """Read in the csv file and return a list of tuples representing the data.

    Transform each field as follows:
      date: datetime.date
      mileage: integer
      location: string
      gallons: float
      price: float (you'll need to get rid of the '$')

    Do not return a tuple for the header row.  While you can process the raw text using string
    functions, to receive full credit you must use Python's built in csv module.

    If the field is blank, you should put a None value in the tuple for that field (for the 
    other functions below, you'll need to check for None values when making calculations).  

    Hint: to parse the date field, use the strptime function in the datetime module, and then
    use datetime.date() to create a date object.

    See: 
      https://docs.python.org/3/library/csv.html
      https://docs.python.org/3/library/datetime.html

    """
    rows = []  # this list should contain one tuple per row
    with open("mustard_data.csv") as mustard:
        reader = csv.reader(mustard)
        next(reader)
        list = []
        for row in reader:
            #date
            if row[0] != "":
                day = datetime.datetime.strptime(row[0], "%m/%d/%Y")
                list.append(day)
            else:
                list.append(None)
            #mileage
            if row[1] != "" :
                list.append(int(row[1]))
            else:
                list.append(None)
            #location
            list.append(row[2])
            #gallons
            if row[3] != "":
                list.append(float(row[3]))
            else:
                list.append(None)
            #price
            if row[4] != "":
                list.append(float(row[4][1:]))
            else:
                list.append(None)
            rows.append(tuple(list))
            list = []
    return rows  # a list of (date, mileage, location, gallons, price) tuples


# Exercise 1. (5 points)
#
def total_cost(rows):
    """Return the total amount of money spent on gas as a float.
    
    Hint: calculate by multiplying the price per gallon with the  number of gallons for each row.
    """
    total = 0
    for r in rows:
        if(r[3] != None) and (r[4] != None):
            total = total + (r[3]*r[4])
    return total


# Exercise 2. (5 points)
#
def num_single_locs(rows):
    """Return the number of refueling locations that were visited exactly once.
    
    Hint: store the locations and counts (as keys and values, respectively) in a dictionary, 
    then count up the number of entries with a value equal to one.  
    """
    count = 0
    dict = {}
    for r in rows:
        if r[2] != None:
            if r[2] in dict:
                dict[r[2]] = dict[r[2]] + 1
            else:
                dict[r[2]] = 1
    for location in dict:
        if dict[location] == 1:
            count = count + 1
    #
    # fill in function body here
    #
    return count


def sortFunc(key):
    return key[1]
# Exercise 3. (8 points)
#
def most_common_locs(rows):
    """Return a list of the 10 most common refueling locations, along with the number of times
    they appear in the data, in descending order.  
    
    Each list item should be a two-element tuple of the form (name, count).  For example, your
    function might return a list of the form: 
      [ ("Honolulu, HI", 42), ("Shermer, IL", 19), ("Box Elder, MO"), ... ]

    Hint: store the locations and counts in a dictionary as above, then convert the dictionary 
    into a list of tuples using the items() method.  Sort the list of tuples using sort() or 
    sorted().

    See:
      https://docs.python.org/3/tutorial/datastructures.html#dictionaries
      https://docs.python.org/3/howto/sorting.html#key-functions
    """

    dict = {}
    for r in rows:
        if r[2] != "":
            if r[2] in dict:
                dict[r[2]] = dict[r[2]] + 1
            else:
                dict[r[2]] = 1
    locations = sorted(dict.items(), key=sortFunc, reverse=True)
    return locations[:10]


# Exercise 4. (8 points)
#
def state_totals(rows):
    """Return a dictionary containing the total number of visits (value) for each state as 
    designated by the two-letter abbreviation at the end of the location string (keys).  

    The return value should be a Python dictionary of the form:
      { "CA": 42, "HI": 19, "MA": 8675309, ... }

    Hint: to do this, you'll need to pull apart the location string and extract the state 
    abbreviation.  Note that some of the entries are malformed, and containing a state code but no
    city name.  You still want to count these cases (of course, if the location is blank, ignore
    the entry.
    """
    dict = {}
    for r in rows:
        if r[2] != "":
            state = r[2][:2]
            if state in dict:
                dict[state] = dict[state] + 1
            else:
                dict[state] = 1
    return dict


# Exercise 5. (8 points)
#
def num_unique_dates(rows):
    """Return the total number unique dates in the calendar that refueling took place.

    That is, if you ignore the year, how many different days had entries? (This number should be 
    less than or equal to 366!)
 
    Hint: the easiest way to do this is create a token representing the calendar day.  These could
    be strings (using strftime()) or integers (using date.toordinal()).  Store them in a Python set
    as you go, and then return the size of the set.

    See:
      https://docs.python.org/3/library/datetime.html#date-objects
    """
    days = set()
    for r in rows:
        date = r[0]
        if date != None:
            days.add(date.strftime("%m/%d"))
    return len(days)


# Exercise 6. (8 points)
#
def month_avg_price(rows):
    """Return a dictionary containing the average price per gallon as a float (values) for each 
    month of the year (keys).

    The dictionary you return should have 12 entries, with full month names as keys, and floats as
    values.  For example:
        { "January": 3.12, "February": 2.89, ... }

    See:
      https://docs.python.org/3/library/datetime.html
    """
    monthTotals = {
        "01": 0,
        "02": 0,
        "03": 0,
        "04": 0,
        "05": 0,
        "06": 0,
        "07": 0,
        "08": 0,
        "09": 0,
        "10": 0,
        "11": 0,
        "12": 0
    }
    avg = {
        "January": 0,
        "February": 0,
        "March": 0,
        "April": 0,
        "May": 0,
        "June": 0,
        "July": 0,
        "August": 0,
        "September": 0,
        "October": 0,
        "November": 0,
        "December": 0
    }
    counts = [0,0,0,0,0,0,0,0,0,0,0,0]
    for r in rows:
        if(r[0] != None) and (r[3] != None) and (r[4] != None):
            month = r[0].strftime("%m")
            cost = r[4]
            monthTotals[month] = monthTotals[month] + cost
            numMonth = int(month)
            counts[numMonth - 1] = counts[numMonth - 1] + 1
    avg["January"] = monthTotals["01"] / counts[0]
    avg["February"] = monthTotals["02"] / counts[1]
    avg["March"] = monthTotals["03"] / counts[2]
    avg["April"] = monthTotals["04"] / counts[3]
    avg["May"] = monthTotals["05"] / counts[4]
    avg["June"] = monthTotals["06"] / counts[5]
    avg["July"] = monthTotals["07"] / counts[6]
    avg["August"] = monthTotals["08"] / counts[7]
    avg["September"] = monthTotals["09"] / counts[8]
    avg["October"] = monthTotals["10"] / counts[9]
    avg["November"] = monthTotals["11"] / counts[10]
    avg["December"] = monthTotals["12"] / counts[11]
    return avg


# EXTRA CREDIT (+10 points)
#
def highest_thirty(rows):
    """Return the start and end dates for top three thirty-day periods with the most miles driven.

    The periods should not overlap.  You should find them in a greedy manner; that is, find the
    highest mileage thirty-day period first, and then select the next highest that is outside that
    window).
    
    Return a list with the start and end dates (as a Python datetime object) for each period, 
    followed by the total mileage, stored in a tuple:  
        [ (1995-02-14, 1995-03-16, 502),
          (1991-12-21, 1992-01-16, 456),
          (1997-06-01, 1997-06-28, 384) ]
    """
    return []


# The main() function below will be executed when your program is run.
# Note that Python does not require a main() function, but it is
# considered good style (as is including the __name__ == '__main__'
# conditional below)
#
def main(file_name):
    rows = read_data(file_name)
    print("Exercise 0: {} rows\n".format(len(rows)))

    cost = total_cost(rows)
    print("Exercise 1: ${:.2f}\n".format(cost))

    singles = num_single_locs(rows)
    print("Exercise 2: {}\n".format(singles))

    print("Exercise 3:")
    for loc, count in most_common_locs(rows):
        print("\t{}\t{}".format(loc, count))
    print("")

    print("Exercise 4:")
    for state, count in sorted(state_totals(rows).items()):
        print("\t{}\t{}".format(state, count))
    print("")

    unique_count = num_unique_dates(rows)
    print("Exercise 5: {}\n".format(unique_count))

    print("Exercise 6:")
    for month, price in sorted(month_avg_price(rows).items(),
                               key=lambda t: datetime.datetime.strptime(t[0], '%B').month):
        print("\t{}\t${:.2f}".format(month, price))
    print("")

    print("Extra Credit:")
    for start, end, miles in sorted(highest_thirty(rows)):
        print("\t{}\t{}\t{} miles".format(start.strftime("%Y-%m-%d"),
                                          end.strftime("%Y-%m-%d"), miles))
    print("")


#########################

if __name__ == '__main__':
    
    data_file_name = "mustard_data.csv" 
    main(data_file_name)




