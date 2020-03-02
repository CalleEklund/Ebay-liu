1. Vad erbjuder Alchemy för fördelar när man arbetar med databaser?
  Alchemy är anpassat för python programmering, detta gör det lättare att
  arbeta med databas data och göra det till python objekt. Det underlättar
  arbetsprocessen i databashanteringen.

2. Vad innebär modulär kod-struktur när det gäller Flask-kod? Hur kan cirkulära importer ställa till det? Har ni hittat någon bra lösning?
  Modulär kod-struktur innebär att man delar upp arbetet i mindre delar i detta
  fall så är det urls och dess indata, vilket gör det lättare att felsöka samt att expandera.
  cirkulära importer kan bero av varandra vilket gör det svårt att hitta felet samt åtgärda det,
  det kan också orsaka öändling rekursion. Helt enkelt göra det svårt att arbeta med.
  Vi har delat upp våran tidigare kod delat upp frågor vid url samt användning,
  detta för att underlätta för oss i senare tillfällen.
3. Vad finns det för problem (och sätt att lösa det) med att uppdatera _strukturen_ på en databas som redan innehåller data? Vad kommer ni att ha för strategi i denna fråga för er app-utveckling?
  Om man uppdaterar strukturen på databasen som redan har befintlig data så kan
  data saknas på de t.ex. befintliga användarna vilket för de till okomplett data.
  Sätt att lösa det på kan vara att tänka ut en grundlig struktur innan man
  börjar detta för att motverka att onödig expandering.
  Vår strategi kommer vara att tänka igenom innan vilken data vi vill ha från
  våra användare, samt vid evetuell utökning fråga användaren om saknad data.
