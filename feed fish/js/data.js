var dataObj = function() {
        this.fruitNum = 0;
        this.double = 1;
        this.score = 0;

        this.gameOver = false;
        this.alpha = 0;
        ctx1.fillStyle = "#fff";
        ctx1.font = '30px Verdana'
        ctx1.textAlign = 'center';

}

dataObj.prototype = {
        reset: function() {
                this.fruitNum = 0;
                this.double = 1;
        },
        draw: function() {
                var w = can1.width;
                var h = can1.height;

                ctx1.save();

                ctx1.shadowBlur = 20;
                ctx1.shadowColor = "blue";
                ctx1.fillText('SCRORE: ' + this.score, w * 0.5, h - 50)

                if (data.gameOver) {


                        this.alpha += deltaTime * 0.001;
                        if (this.alpha > 1) {
                                this.alpha = 1;
                        }
                        ctx1.fillStyle = "rgba(255,255,255," + this.alpha + ")";
                        ctx1.fillText('Game Over', w * 0.5, h * 0.5);


                }

                ctx1.restore();

        },
        addScore: function() {
                this.score += this.fruitNum * 100 * this.double;
                this.reset();
        }
}