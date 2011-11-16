
var qsa = 0;
var rewardForStateQ = 0;
var qsaPrime = 100;

// Falloff properties
var learningRate = 0.5;
var gamma = 0.9;


qsa = qsa + learningRate * ( rewardForStateQ + gamma*(qsaPrime-qsa) )
console.log("LearningRate Alpha:\t\t" + learningRate );
console.log("decay Gamma: \t\t\t" + gamma );
console.log("Q of sPrime given aPrime: \t" + qsaPrime);
console.log("Final Result:\t\t\t" + qsa);
