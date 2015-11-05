var aneObj = function() {

        this.rootx = [];
        this.headx = [];
        this.heady = [];
        this.angle = 0;
        this.amp = [];
}

aneObj.prototype = {
        num: 50,
        init: function() {
                for (var i = 0; i < this.num; i++) {
                        this.rootx[i] = i * 20 + Math.random() * 22;
                        this.headx[i] = this.rootx[i];
                        this.heady[i] = canHeight - 200 + Math.random() * 50;
                        this.amp[i] = Math.random() * 50 + 50;
                }
        },
        draw: function() {
                //：beginPath,moveTo,lineTo,stroke,strokeStyle,lineWidth,lineCap,globalAlpha
                this.angle += deltaTime * 0.0008;
                var l = Math.sin(this.angle); //[-1,1]
                ctx2.save();
                ctx2.globalAlpha = 0.6;
                ctx2.lineWidth = 20;
                ctx2.lineCap = 'round';
                ctx2.strokeStyle = '#3b154e';
                for (var i = 0; i < this.num; i++) {
                        ctx2.beginPath();

                        ctx2.moveTo(this.rootx[i], canHeight);
                        this.headx[i] = this.rootx[i] + l * this.amp[i];
                        ctx2.quadraticCurveTo(this.rootx[i], canHeight - 100, this.headx[i], this.heady[i]);

                        ctx2.stroke();
                }
                ctx2.restore();
        }
}