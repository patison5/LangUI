# LangUI - Интерпретатор моего языка
[![Build Status](https://travis-ci.org/joemccann/dillinger.svg?branch=master)](https://travis-ci.org/joemccann/dillinger)

# Описание проекта
#

###### В проект входит(будет входить):

| Название | Описание | 
| ------ | ------ |
| Лексер (Токенайзер) |  Читает файл с моей программой program.my и разбивает его на список отдельных строковых переменных, называемых токенами
| Парсер | Проверяет список токенов на соответствие написанной в моем языке грамматики
|  Интерпретатор | Выполняет написанный код программы

#
### Пример тестируемой программы
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
    printf("Roma pidr");
}

while (false) {
    printf("Fedya pidr");
}
```

