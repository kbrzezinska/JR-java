var fun = function(list) {
var jsArray = Java.from(list);
    var min = 0;
    var max = jsArray.length-1;
    var count=0;
   // for(var i=0;i<25;i++){print(jsArray[i]+" "+i);}
   while(true)
    {
    var rand = Math.floor(Math.random() * (max - min + 1)) + min;

    if (jsArray[rand]===null)
    {

            print("rand: "+rand+" "+max+" ==="+jsArray[rand]);
          jsArray[rand]='x';
          return rand;
    }
    count++;
    if(count==100)
    {
     for(var i=0;i<25;i++){

     if (jsArray[i]===null  )
         {
                 print("count: "+rand+" "+max);
               jsArray[i]='x';
               return i;
         }

     }

    }


    }
    return rand;
};