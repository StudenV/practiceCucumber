# language: ru
@верно
Функция: Операции с таблицей FOOD

  @верно
  Сценарий: Получение всех записей
    Дано Я подключился к базе данных
    Когда Я запрашиваю записи из таблицы FOOD
    Тогда Я должен увидеть как минимум одну запись

  @верно
  Сценарий: Добавление нового товара
    Дано Я подключился к базе данных
    Когда Я добавляю новый товар с ID 5 и названием "Банан"
    Тогда Товар должен быть добавлен в базу данных
    И Я должен смочь получить товар с ID 5

  @верно
  Сценарий: Удаление товара
    Дано Я подключился к базе данных
    Когда Я удаляю товар с ID 5
    Тогда Товар должен быть удалён из базы данных

  @верно
  Сценарий: Проверка текущего состояния таблицы
    Дано Я подключился к базе данных
    Когда Я запрашиваю записи из таблицы FOOD
    Тогда Я должен увидеть все записи
