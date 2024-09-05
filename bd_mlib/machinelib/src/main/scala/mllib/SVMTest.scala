package mllib

import org.apache.spark.mllib.classification.SVMWithSGD
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.mllib.evaluation.MulticlassMetrics
import org.apache.spark.mllib.util.MLUtils

object SVMTest {

  def main(args: Array[String]) {
    //1 读取样本数据

    val data_path = "/user/tmp/sample_libsvm_data.txt"

    val conf = new SparkConf();
    val sc = new SparkContext(conf);
    val examples = MLUtils.loadLibSVMFile(sc, data_path).cache()

    //2 样本数据划分训练样本与测试样本

    val splits = examples.randomSplit(Array(0.6, 0.4), seed = 11L)
    val training = splits(0).cache()
    val test = splits(1)
    val numTraining = training.count()
    val numTest = test.count()
    println(s"Training: $numTraining, test: $numTest.")

    //3 新建SVM模型，并设置训练参数

    val numIterations = 1000    //迭代次数，默认100
    val stepSize = 1            //迭代步长，默认1
    val miniBatchFraction = 1.0    //每次迭代参与计算的样本比例，默认为1.0
    val model = SVMWithSGD.train(training, numIterations, stepSize, miniBatchFraction)

    //4 对测试样本进行测试
    val prediction = model.predict(test.map(_.features))

    //5  计算测试误差
    val predictionAndLabel = prediction.zip(test.map(_.label))
    val metrics = new MulticlassMetrics(predictionAndLabel)

    val precision = metrics.precision

    println("Precision = " + precision)

  }


}