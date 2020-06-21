# LangUI - Интерпретатор языка F++
[![Build Status](https://travis-ci.org/joemccann/dillinger.svg?branch=master)](https://travis-ci.org/joemccann/dillinger)

# Описание проекта
###### (Проект создается исключительно в обучающихся целях :D)

### В проект входит:
###### (будет входить)

| Название | Описание | 
| ------ | ------ |
| Лексер (Токенайзер) |  Читает файл с программой program.my и разбивает его на список отдельных строковых переменных, называемых токенами
| Парсер | Проверяет список токенов на соответствие написанной грамматики
| Полис | Сортирует список токенов в формате обратной польской записи
|  Интерпретатор | Выполняет написанный код программы

#
### Пример того, что я хочу получить
```sh
int k = 12;

for (int i = 0; i < 12; i = 2) {
    if (i == 6) {
        break;
    } else {
        printf(i);
    }
} 
// Вывод: 0,1,2,3,4,5,6

int fib (n) {
  if (n <= 1) {
      return n;
  } else {
      return fib(n - 1) + fib(n - 2);
  }
}

printf( fib(3) ); // 2
printf( fib(7) ); // 13
```

### Пример того, что я имею сейчас
```sh
HashMap map;
map HashAdd keyA 12;
map HashAdd keyB 13;
int tmp = map HashGet keyA;

int i = 0;
list aList;
while (i < 10) {
    aList add i;
    i = i + 1;
}
int b = aList get 0;
int c = aList get 1;
int k = 0;
int tmp = 0;
while (k < 10) {
    tmp = aList get k;
    printf(tmp);
    k = k + 1;
}

int c = 0;
while (i < 22) {
    printf(i);
     i = i + 2;
    if (i < 1) {
       printf(i);
    if (c > 1) {
       printf(c);
    }
    i = i + 1;
}
```

### Типы данных
| Тип | значение | 
| ------ | ------ |
|``` int``` | Целочисленный типа данных 
|``` bool```| Булевый тип данных 
| ```string```| Строковый тип данных 
| ```void``` | "Пустой" тип данных 

#
###### Примеры использования

```sh
int k = 12;
bool flag = true;
float f = 12.13;

void func () { return; }
```
#
###### Вывод сообщений на экран консоли
```sh
printf("Hello world");
```


#
### Циклы
| Тип | Описание | параметры 
| ------ | ------ | ------ |
|``` for``` | цикл с явно заданным счетчиков и условием выхода | for (int i = 0; i < 15; i = i + 1) {  }
|``` while```| цикл с неявно заданным условием выхода | while (true) { }

#
###### Примеры использования
```sh
for (int i = 0; i < 15; i++) {
    printf("Hello world!");
}

while (false) {
    printf("Hello world!");
}
```

