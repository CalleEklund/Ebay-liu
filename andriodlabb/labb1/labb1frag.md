  1.  Vad är Androids 'R' för något, vad innehåller den för information, hur genereras den? Hur används den i Android-kod, t.ex. i onCreate i en Activity?

  R är huvudklass som genereras när en activity skapas som innehåller information om bland annat strängar, layouts och element.
  Den är själva kopplingen mellan xml (design) filen och huvudprogrammet. Den kan använda för att nå element i en activity.

  2.  Vad är Gradle? Vad innebär Gradle sync?
  Gradle är ett inbyggt build verktyg som håller reda på "dependencies" och andra inställningar som används när appen buildar.
  Gradle sync uppdaterar ditt program mot de "dependencies" som finns angivit i build.gradle filen

  3.  Vad är Instant Run i Android Studio?
  Används för att kunna se skriven kod utan att behöva bygga och kompilera om programmet från början,
  detta sänker tiden det tar för programmet att applicera förändringar från koden.
