1. Hur inkluderar man externa bibliotek, t.ex. Volley, i ett Android-projekt?
  Man lägger till följande kod i sin module build.gradel fil: implementation 'com.android.volley:volley:1.1.1'
  vilket berätta för gradle att modulen kommer att använda kod från volley biblioteket.

2. Vad är Java beans för något? Vad är det för fördel med att använda biblioteket gson, gentemot den inbyggda json-parsern?
  Java beans används för att inkapsla kod, det är det som gör att java kod kan köras på vilket dator som helst. Nackdelen med att
  andvända den inbyggda parseren är den behöver läsa in hela JSON documenetet för att sedan göra om det till en sträng.
  Gson kan retunera JSONObject eller JSONArrays vilket gör det mycket läggare att hantera data.

3. Vad är den viktigaste, mest centrala frågan, som ni har behövt Googla på under laborationens gång?
  How parse JSONArrays into JSONObjects?
