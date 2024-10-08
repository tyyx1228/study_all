/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

// scalastyle:off println
package official

// $example on$
import org.apache.spark.mllib.regression.{IsotonicRegression, IsotonicRegressionModel}
// $example off$
import org.apache.spark.{SparkConf, SparkContext}

object IsotonicRegressionExample {

  def main(args: Array[String]) : Unit = {

    val conf = new SparkConf().setAppName("IsotonicRegressionExample")
    val sc = new SparkContext(conf)
    // $example on$
    val data = sc.textFile("data/mllib/sample_isotonic_regression_data.txt")

    // Create label, feature, weight tuples from input data with weight set to default value 1.0.
    val parsedData = data.map { line =>
      val parts = line.split(',').map(_.toDouble)
      (parts(0), parts(1), 1.0)
    }

    // Split data into training (60%) and test (40%) sets.
    val splits = parsedData.randomSplit(Array(0.6, 0.4), seed = 11L)
    val training = splits(0)
    val test = splits(1)

    // Create isotonic regression model from training data.
    // Isotonic parameter defaults to true so it is only shown for demonstration
    val model = new IsotonicRegression().setIsotonic(true).run(training)

    // Create tuples of predicted and real labels.
    val predictionAndLabel = test.map { point =>
      val predictedLabel = model.predict(point._2)
      (predictedLabel, point._1)
    }

    // Calculate mean squared error between predicted and real labels.
    val meanSquaredError = predictionAndLabel.map { case (p, l) => math.pow((p - l), 2) }.mean()
    println("Mean Squared Error = " + meanSquaredError)

    // Save and load model
    model.save(sc, "target/tmp/myIsotonicRegressionModel")
    val sameModel = IsotonicRegressionModel.load(sc, "target/tmp/myIsotonicRegressionModel")
    // $example off$
  }
}
// scalastyle:on println
