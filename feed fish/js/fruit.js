var fruitObj = function() {
        this.alive = [];
        this.x = [];
        this.y = [];
        this.aneNO = [];
        this.l = [];
        this.spd = [];
        this.fruitType = [];
        this.orange = new Image();
        this.blue = new Image();
}

fruitObj.prototype = {
        num: 30,
        init: function() {
                for (var i = 0; i < this.num; i++) {
                        this.alive[i] = false;
                        this.x[i] = 0;
                        this.y[i] = 0;
                        this.aneNO[i] = 0;
                        this.l[i] = 0;
                        this.spd[i] = Math.random() * 0.04 + 0.02;
                        this.fruitType[i] = "";
                }
                this.orange.src = './src/fruit.png';
                this.blue.src = './src/blue.png';
        },
        draw: function() {
                this.fruitMonitor();
                for (var i = 0; i < this.num; i++) {
                        if (this.alive[i]) {
                                if (this.fruitType[i] === 'blue') {
                                        var pic = this.blue;
                                } else {
                                        var pic = this.orange;
                                }
                                if (this.l[i] <= 14) {
                                        var NO=this.aneNO[i]
                                        this.x[i] = ane.headx[NO];
                                        this.y[i] = ane.heady[NO];
                                        this.l[i] += this.spd[i] * deltaTime;
                                
                                } else {

                                        this.y[i] -= this.spd[i] * deltaTime
                                                
                                }

                                ctx2.drawImage(pic, this.x[i] - this.l[i] * 0.5, this.y[i] - this.l[i] * 0.5, this.l[i], this.l[i]);

                                if (this.y[i] < 10) {

                                        this.alive[i] = false;
                                }
                        }

                }
        },
        born: function(i) {

                this.aneNO[i] = Math.floor(Math.random() * ane.num);
                var ran = Math.random();
                this.l[i] = 0;
                this.alive[i] = true;
                if (ran < 0.3) {
                        this.fruitType[i] = 'blue';
                } else {
                        this.fruitType[i] = 'orange';
                }

        },
        fruitMonitor: function() {
                var num = 0
                for (var i = 0; i < this.num; i++) {
                        if (this.alive[i]) num++;
                }
                if (num < 15) {
                        this.sendFruit();
                        return;
                }
        },
        sendFruit: function() {
                for (var i = 0; i < this.num; i++) {
                        if (!this.alive[i]) {
                                this.born(i);
                                return;
                        }
                }
        },
        dead: function(i) {
                this.alive[i] = false;
        }
}