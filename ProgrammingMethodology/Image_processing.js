
let robot = 
  lib220.loadImageFromURL('https://people.cs.umass.edu/~joydeepb/robot.jpg');

function calcMean(numbas){
  let result = 0;
  for(let i = 0; i < numbas.length; ++i){
    result = result + numbas[i];
  }
  result = result / numbas.length;
  return result;
}

function getBlur(x, y, image){
  let means = image.getPixel(x, y);
  let cntr = 1;
  for(let i = x; i < Math.min(x + 5, image.width); ++i){
    let rgb = image.getPixel(i, y);
    means[0] = means[0] + rgb[0];
    means[1] = means[1] + rgb[1];
    means[2] = means[2] + rgb[2];
    cntr += 1;
  }
  for(let i = x; i > Math.max(0, x - 5); --i){
    let rgb = image.getPixel(i , y);
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

function removeBlueAndGreen(image){
  let result = image.copy();
  for(let i = 0; i < result.width; ++i){
    for(let j = 0; j < result.height; ++j){
      result.setPixel(i, j, [result.getPixel(i, j)[0], 0.0, 0.0]);
    }
  }
  return result
}

function makeGrayscale(image){
  let result = image.copy();
  for(let i = 0; i < result.width; ++i){
    for(let j = 0; j < result.height; ++j){
      let mean = calcMean(result.getPixel(i, j));
      result.setPixel(i, j, [mean, mean, mean]);
    }
  }
  return result;
}

function highlightEdges(image){
  let result = image.copy();
  for(let i = 0; i < result.width; ++i){
    for(let j = 0; j < result.height; ++j){
      let m1 = calcMean(result.getPixel(i, j));
      if(i < result.width - 1){
        let m2 = calcMean(result.getPixel(i + 1, j));
        let abs = Math.abs(m1 - m2);
        result.setPixel(i, j, [abs, abs, abs]);
      }
      else{
        result.setPixel(i, j, [m1, m1, m1]);
      }
    }
  }
  return result;
}

function blur(image){
  let result = image;
  for(let i = 0; i < result.width; ++i){
    for(let j = 0; j < result.height; ++j){
      let means = getBlur(i, j, image);
      result.setPixel(i, j, means);
    }
  }
  return result;
}
let red = removeBlueAndGreen(robot);
let gray = makeGrayScale(robot);
let highlight = highLightEdges(robot);
let b = blur(robot)
b.show();
red.show();
gray.show();
highlight.show();
robot.show();