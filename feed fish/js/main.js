var can1;
var can2;

var ctx1;
var ctx2;

var canWidth;
var canHeight;

var lastTime;
var deltaTime;

var ane;
var fruit;
var mom;
var baby;
var dust;

var babyTail = [];
var babyEye = [];
var babyBody = [];
var momTail = [];
var momEye = [];
var momBodyBlue = [];
var momBodyOrange = [];
var dustPic = [];

var data;

var mx;
var my;

var bgPic = new Image();

document.body.onload = function(){
	new game();
	
}

function game(){
	init();
	lastTime = Date.now();
    deltaTime = 0;
    gameloop();
}

function init(){
	 can1 = document.querySelector('#canvas1');
     ctx1 = can1.getContext('2d');
     can2 = document.querySelector('#canvas2');
     ctx2 = can2.getContext('2d');

     can1.addEventListener('mousemove', onMouseMove, false);

     bgPic.src = './src/background.jpg';

     canWidth = can1.width;
     canHeight = can1.height

     ane = new aneObj();
     ane.init();

     fruit = new fruitObj();
     fruit.init();

      mx = canWidth * 0.5;
      my = canHeight * 0.5;

     
     mom = new momObj();
     mom.init();

     baby = new babyObj();
     baby.init();

     data = new dataObj();

     dust = new dustObj();
     dust.init();

    for (var i = 0; i < 8; i++) {
        babyTail[i] = new Image();
        babyTail[i].src = './src/babyTail' + i + '.png';
    }

    for(var i=0; i<2; i++){
        babyEye[i] = new Image();
        babyEye[i].src = './src/babyEye' + i + '.png';
    }

    for (var i = 0; i < 20; i++) {
        babyBody[i] = new Image();
        babyBody[i].src = './src/babyFade' + i + '.png';
    }

    for (var i = 0; i < 8; i++) {
        momTail[i] = new Image();
        momTail[i].src = './src/bigTail' + i + '.png';
    }

    for(var i=0; i<2; i++){
        momEye[i] = new Image();
        momEye[i].src = './src/bigEye' + i + '.png';
    }

    for (var i = 0; i < 8; i++) {
        momBodyOrange[i] = new Image();
        momBodyBlue[i] = new Image();
        momBodyOrange[i].src = './src/bigSwim' + i + '.png';
        momBodyBlue[i].src = './src/bigSwimBlue' + i + '.png';
    }

    for(var i=0; i<7; i++){
        dustPic[i] = new Image();
        dustPic[i].src = './src/dust' + i + '.png';
    }


}

function gameloop(){
	window.requestAnimFrame(gameloop);
	var now = Date.now();
	deltaTime = now - lastTime;
	lastTime = now;
	
	drawBackground();

	ane.draw();

	fruit.draw();

	ctx1.clearRect(0, 0, canWidth, canHeight);
	mom.draw();

	baby.draw();

    //collison judge
	momFruitsCollision();
    momBabyCollision();
    
    //record score
    data.draw();

    //draw dust
    dust.draw();
}


function onMouseMove(e) {
        if (e.offSetX || e.layerX && !data.gameOver) {
                mx = e.offSetX == undefined ? e.layerX : e.offSetX;
                my = e.offSetY == undefined ? e.layerY : e.offSetY;
        }
}