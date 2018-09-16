#include "iostream"
#include "map"

void igor() {
  std::cout << " question methoe igor" << std::endl;
}

float tofu(int n) {
  return n * 1.0;
}

double mpg(double d1, double d2) {
  return d1 + d2;
}

long summation(const long* ls, int length) {
  long total = 0;
  for (int i = 0; i < length; i++) {
    total += ls[i];
  }
  return total;
}

double doctor(std::string str) {
  int length = 0;
  for (int i = 0; str[i] != '\0'; i++) {
    length++;
  }
  return length * 1.0;
}

struct boss {
  std::string name;
  std::string sex;
};

void ofcourse(boss bs) {
  std::cout << "name: " << bs.name << "sex: " << bs.sex << std::endl;
}

std::string plot(std::map<int, std::string> maps) {
  std::string result;

  std::map<int, std::string>::iterator iter;
  iter = maps.begin();
  while (iter != maps.end()) {
    std::cout << iter->first << " : " << iter->second << std::endl;
    result.append(iter->second);
    iter++;
  }

  // 也可以使用for循环遍历
  /*
   for(iter = _map.begin(); iter != _map.end(); iter++) {
   cout << iter->first << " : " << iter->second << endl;
   }
   */
  return result;
}

void int_array_set_one_value(int* ints, const int length, const int set_value) {
  for (int i = 0; i < length; i++) {
    ints[i] = set_value;
  }
}

void int_array_set_one_value(int* start, int* end, const int set_value) {
  int* pt;
  for (pt = start; pt != end; pt++) {
    *pt = set_value;
  }
}

double get_max_double_value(const double* ds, const int length) {
  double result = 0;
  for (int i = 0; i < length; i++) {
    if (ds[i] > result) {
      result = ds[i];
    }
  }
  return result;
}

int replace(std::string str, char oldchar, char newchar) {
  std::cout << "in string: " << str << std::endl;
  std::string new_str = std::string(1, newchar);
  int count = 0;
  //  int length = str.length();
  //  for (int i = 0; i < length; i++) {
  //    if (str[i] == oldchar) {
  //      std::cout << "now char: " << str[i] << std::endl;
  //      //      str[i] = newchar;
  //      str.replace(i, i + 1, new_str);
  //      count++;
  //    }
  //  }
  char* ss;
  ss = &str[0];
  for (; *ss != '\0'; ss++) {
    if (*ss == oldchar) {
      *ss = newchar;
      count++;
    }
  }
  std::cout << "string is : " << str << std::endl;
  return count;
}

int replace(char* str, char oldchar, char newchar) {
  int result = 0;

  for (int i = 0; str[i] != '\0'; i++) {
    if (str[i] == oldchar) {
      str[i] = newchar;
      result++;
    }
  }
  std::cout << "string is : " << str << std::endl;
  return result;
}


