var momObj = function(){
	this.x;
	this.y;
    this.angle;
	this.bigBody = new Image();
	
	this.momTailTimer = 0;
    this.momTailCount = 0;

    this.momEyeTimer = 0;
    this.momEyeCount = 0;
    this.momEyeInterval = 1000;

    this.bigBodyCount = 0;


}

momObj.prototype.init = function(){
	this.x = canWidth*0.5;
	this.y = canHeight*0.5;
    this.angle = 0;

	
	this.bigBody.src = "./src/bigSwim0.png"
	
}

momObj.prototype.draw = function(){
	   this.x = lerpDistance(mx, this.x, 0.97);
	   this.y = lerpDistance(my, this.y, 0.97);

	   var deltaY = my - this.y;
	   var deltaX = mx - this.x;

	   var beta = Math.atan2(deltaY, deltaX) + Math.PI;

     //tail time counter
     this.momTailTimer += deltaTime;
     if (this.momTailTimer > 50) {
           this.momTailCount = (this.momTailCount + 1) % 8;
           this.momTailTimer %= 50;
     }

     //eye time counter
     this.momEyeTimer += deltaTime;
        if (this.momEyeTimer > this.momEyeInterval) {
            this.momEyeCount = (this.momEyeCount + 1) % 2;
            this.momEyeTimer %= this.momEyeInterval;
                if (this.momEyeCount === 0) {
                   this.momEyeInterval = Math.random() * 1500 + 2000;
                } else {
                    this.momEyeInterval = 200;
                }
        }

    this.angle = lerpAngle(beta, this.angle, 0.9);
	ctx1.save();
	ctx1.translate(this.x,this.y);
    ctx1.rotate(this.angle);

    //draw tial
    var momTailCount = this.momTailCount;
	ctx1.drawImage(momTail[momTailCount], -momTail[momTailCount].width * 0.5 + 30, -momTail[momTailCount].height * 0.5 + 2);


	//draw body
	var bigBodyCount = this.bigBodyCount;
    if (data.double === 1) {
            ctx1.drawImage(momBodyOrange[bigBodyCount], -momBodyOrange[bigBodyCount].width * 0.5, -momBodyOrange[bigBodyCount].width * 0.5);
    } else {
            ctx1.drawImage(momBodyBlue[bigBodyCount], -momBodyBlue[bigBodyCount].width * 0.5, -momBodyBlue[bigBodyCount].width * 0.5);
    }
                
    //draw eye
	var momEyeCount = this.momEyeCount;
    ctx1.drawImage(momEye[momEyeCount], -momEye[momEyeCount].width * 0.5, -momEye[momEyeCount].height * 0.5 + 2);
	
	ctx1.restore();
}