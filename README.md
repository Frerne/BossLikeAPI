# BossLikeAPI
Бот Апи клиент bosslike.ru для котлина.
## 🛠 Установка
Добавляем в build.gradle в корневой папке следующие строчки (добавляем jitpack):
```gradle
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```
И так же добавляем в модуле:
```gradle
dependencies {
	  implementation 'com.github.justneon33:BossLikeAPI:1.0'
}
```
## 🚴 Использование
К примеру возьмем синглтон Users и попробуем зарегистрировать пользователя:
```kotlin
Users.create("example@email.net", "strong_pa$\$word") {
    if (success) {
        println(data?.token) // сохраните ваш токен
}
```
Попробуем так же получить данные о пользователе:
```kotlin
Users.get(data!!.token.key) {
    if (success) {
        print(data?.email) // выводим e-mail
    }
}
```
Так же есть такие методы как:
+ UserSocial
  + all (Получение всех аккаунтов которые привязаны к аккаунту)
  + linkAccountByLike (привязать аккаунт по лайку)
  + getTaskForConfirmation (получить задание для подтверждения по лайку)
  + checkLike (проверить выполнение)
  + linkAccountByPhone (привязать по телефону)
  + checkByCode (проверить код что пришел на телефон)
+ Tasks
  + all (все задания)
  + hide (спрятать задание)
  + initialize (инициализировать выполнение задания)
  + check (проверить выполнение)
+ Coupon
  + get (получить информацию о купоне)
## 🎯 Контакты 
Мой телеграмм - https://t.me/y9neon

Библиотека разрабатываеться для себя, пишите что нужно добавить обезательно добавлю.


