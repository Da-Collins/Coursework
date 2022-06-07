let url = 'https://people.cs.umass.edu/~joydeepb/robot.jpg';
let robot = lib220.loadImageFromURL(url);
function imageMap(img, func){
  let image = img.copy();
  for(let i = 0; i < image.width; ++i){
    for(let j = 0; j < image.height; ++j){
      image.setPixel(i , j, func(image, i, j));
    }
  }
  return image;
}

function imageMask(img, func, maskValue){
  function mask(image, x, y){
    if(func(image, x, y)){
      return maskValue;
    }
    return image.getPixel(x, y);
  }
  return imageMap(img, mask);
}

function blurPixel(img, x, y){
  let means = img.getPixel(x, y);
  let cntr = 1;
  for(let i = x; i < Math.min(x + 5, img.width); ++i){
    let rgb = img.getPixel(i, y);
    means[0] = means[0] + rgb[0];
    means[1] = means[1] + rgb[1];
    means[2] = means[2] + rgb[2];
    cntr += 1;
  }
  for(let i = x; i > Math.max(0, x - 5); --i){
    let rgb = img.getPixel(i , y);
    means[0] = means[0] + rgb[0];
    means[1] = means[1] + rgb[1];
    means[2] = means[2] + rgb[2];
    cntr += 1;
  }

  for(let i = 0; i < means.length; ++i){
    means[i]  = means[i] / cntr;
  }
  return means
}

function blurImage(img){
  return imageMap(img, blurPixel);
}

function isDark(img, x, y){
  return img.getPixel(x, y).reduce(
    function(curr, state){return Math.max(curr, state);}, 0.0) <= 0.5;
}

function darken(img){
  return imageMask(img, isDark, [0, 0, 0]);
}

function isLight(img, x, y){
  return img.getPixel(x, y).reduce(
    function(curr, state){return Math.min(curr, state);}, 1.0) >= 0.5;
}

function lighten(img){
  return imageMask(img, isLight, [1, 1, 1]);
}

function lightenAndDarken(img){
  return imageMask(imageMask(img, isLight, [1,1,1]), isDark, [0,0,0]);
}

//blurImage(robot).show();
//robot.show()
//darken(robot).show();
//lighten(robot).show();
lightenAndDarken(robot).show();

//imageMap(robot, function(img, x, y) 
//{  const c = img.getPixel(x, y);  return [c[0], 0, 0];}).show();

//imageMask(robot, function(img, x, y) 
//{  return (y % 10 === 0);}, [1, 0, 0]).show();