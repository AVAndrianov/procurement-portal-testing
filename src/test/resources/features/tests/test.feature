
   Feature: проверка №1,

     Scenario: отерыть первую вакансию на сайте Госслужба
       Given открытие "http://www.zakupki.gov.ru"
       When пользователь проверяет, что находится на странице "Главная Портал Закупок"
       Then пользователь (нажимает кнопку) в выподающем меню Организации с параметром Реестр зарегистрированных организаций
       Then пользователь (нажимает кнопку) Уточнить параметры поиска
       Then пользователь заполняет форму
       Then пользователь (нажимает кнопку) с параметром Уточнить результаты
       Then пользователь сохраняет в файл данные по каждому результату поиска Сокращенное наименование, Дата регистрации и Место нахождения
