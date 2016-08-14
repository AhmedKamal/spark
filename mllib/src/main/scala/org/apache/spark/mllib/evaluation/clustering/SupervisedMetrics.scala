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

package org.apache.spark.mllib.evaluation.clustering

import org.apache.spark.internal.Logging
import org.apache.spark.rdd.RDD

class SupervisedMetrics(val predAndLabels: RDD[(Integer, Integer)], n : Integer) extends Logging {

  /**
   * Returns score between 0.0 and 1.0. 1.0 stands for perfectly complete labeling
   *
   * @see https://en.wikipedia.org/wiki/Mutual_information
   */
  def normalizedMutualInfo(): Double = {

    0
  }

  def mutualInfo(): Double = {

    0
  }

  def contingencyMatrix(): RDD[((Integer, Integer), Double)] = {
    val preds = predAndLabels.map(_._1)
    val labels = predAndLabels.map(_._2)

    val groupedPreds = classProbability(preds)
    val groupedLabels = classProbability(labels)

    val matrix: RDD[((Integer, Double), (Integer, Double))] = groupedLabels.cartesian(groupedPreds)
    matrix.map{case (labels, preds) => {
      ((labels._1, preds._1), (labels._2, preds._2, labels._2 * preds._2))
    }}
  }

  def entropy(clusteredData: RDD[Integer]): Double = {
    clusteredData.map(_ -> 1).reduceByKey(_ + _).map {
      case (label, count) =>
        val labelProbability = (label, count.toDouble / n)
        labelProbability._2 * Math.log(labelProbability._2)
    }.sum()
  }

  def classProbability(clusteredData: RDD[Integer]) : RDD[(Integer, Double)] = {
    clusteredData.map(_ -> 1).reduceByKey(_ + _).map {
      case (label, count) =>
        val labelProbability: (Integer, Double) = (label, count.toDouble / n)
        labelProbability
    }
  }
}
