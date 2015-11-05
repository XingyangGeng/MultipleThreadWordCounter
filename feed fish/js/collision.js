function momFruitsCollision() {

        if (!data.gameOver) {
                for (var i = 0; i < fruit.num; i++) {
                        if (fruit.alive[i]) {
                                var l = calLength2(fruit.x[i], fruit.y[i], mom.x, mom.y);
                                if (l < 900) {
                                        fruit.dead(i);
                                        data.fruitNum++;
                                        mom.bigBodyCount++;
                                        if (mom.bigBodyCount >= 7) {
                                                mom.bigBodyCount = 7;
                                        }
                                        if (fruit.fruitType[i] === 'blue') {
                                                data.double = 2;
                                        }
                                        
                                }
                        }
                }
        }

}

function momBabyCollision() {

        var l = calLength2(mom.x, mom.y, baby.x, baby.y);

        if (l < 900 && data.fruitNum > 0 && !data.gameOver) {
                baby.babyBodyCount = 0;
                mom.bigBodyCount = 0;
                data.addScore();
                
        }
}