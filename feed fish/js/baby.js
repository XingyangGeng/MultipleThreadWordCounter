var babyObj = function(){
	this.x;
	this.y;
	this.angle;
	this.babyEye = new Image();
	this.babyBody = new Image();
	
	this.babyTailTimer = 0;
    this.babyTailCount = 0;

    this.babyEyeTimer = 0;
    this.babyEyeCount = 0;
    this.babyEyeInterval = 1000;

    this.babyBodyTimer = 0;
    this.babyBodyCount = 0;



}

babyObj.prototype.init = function(){
   this.x = canWidth*0.5-100;
   this.y = canHeight*0.5+100;
   this.angle = 0;

}

babyObj.prototype.draw = function(){
    this.x = lerpDistance(mom.x, this.x, 0.97);
    this.y = lerpDistance(mom.y, this.y, 0.97);

    var deltaY = mom.y - this.y;
    var deltaX = mom.x - this.x;

     //tail time counter
     this.babyTailTimer += deltaTime;
     if (this.babyTailTimer > 50) {
           this.babyTailCount = (this.babyTailCount + 1) % 8;
           this.babyTailTimer %= 50;
     }

     //eye time counter
     this.babyEyeTimer += deltaTime;
     if (this.babyEyeTimer > this.babyEyeInterval) {
            this.babyEyeCount = (this.babyEyeCount + 1) % 2;
            this.babyEyeTimer %= this.babyEyeInterval;
            if (this.babyEyeCount === 0) {
                  this.babyEyeInterval = Math.random() * 1500 + 2000;
            } else {
                   this.babyEyeInterval = 200;
            }
      }
     
     //body color time counter
     this.babyBodyTimer += deltaTime;
     if (this.babyBodyTimer > 300) {
          this.babyBodyCount = this.babyBodyCount + 1;
          this.babyBodyTimer %= 300;
          if (this.babyBodyCount > 19) {
               this.babyBodyCount = 19;
                data.gameOver = true;
           }
     }



    var beta = Math.atan2(deltaY, deltaX) + Math.PI;
    this.angle = lerpAngle(beta, this.angle, 0.9);
    
	ctx1.save();
	ctx1.translate(this.x,this.y);
    ctx1.rotate(this.angle);
	
	
    //draw tial
    var babyTailCount = this.babyTailCount;
	ctx1.drawImage(babyTail[babyTailCount], -babyTail[babyTailCount].width * 0.5 + 24, -babyTail[babyTailCount].height * 0.5 + 1, 27, 37);

    //draw body
    var babyBodyCount = this.babyBodyCount;
    ctx1.drawImage(babyBody[babyBodyCount], -babyBody[babyBodyCount].width * 0.5, -babyBody[babyBodyCount].height * 0.5);

    //draw eye
	var babyEyeCount = this.babyEyeCount;
    ctx1.drawImage(babyEye[babyEyeCount], -babyEye[babyEyeCount].width * 0.5, -babyEye[babyEyeCount].height * 0.5 + 2);
	

	ctx1.restore();
}