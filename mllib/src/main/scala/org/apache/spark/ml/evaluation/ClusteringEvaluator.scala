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

package org.apache.spark.ml.evaluation

import org.apache.spark.annotation.Since
import org.apache.spark.ml.param._
import org.apache.spark.ml.param.shared._
import org.apache.spark.ml.util.Identifiable
import org.apache.spark.sql.Dataset

/**
 * Evaluator for clustering,
 * For supervised metrics, it expects two input columns: rawPrediction and label
 * For unsupervised metrics, it expects two input columns: rawPrediction and featureColumn
 */
class ClusteringEvaluator extends Evaluator
  with HasPredictionCol with HasLabelCol with HasFeaturesCol {
  @Since("2.0.0")
  def this() = this(Identifiable.randomUID("clusterEval"))

  /**
   * param for metric name in evaluation (supports `"silhouette"` (default), `"mutual_info"`)
   *
   * @group param
   */
  @Since("2.0.0")
  val metricName: Param[String] = {
    val allowedParams = ParamValidators.inArray(Array("silhouette", "mutual_info"))
    new Param(this, "metricName", "metric name in evaluation " +
      "(silhouette|mutual_info)", allowedParams)
  }

  /** @group getParam */
  @Since("2.0.0")
  def getMetricName: String = $(metricName)

  /** @group setParam */
  @Since("2.0.0")
  def setMetricName(value: String): this.type = set(metricName, value)

  /** @group setParam */
  @Since("2.0.0")
  def setPredictionCol(value: String): this.type = set(predictionCol, value)

  /** @group setParam */
  @Since("2.0.0")
  def setLabelCol(value: String): this.type = set(labelCol, value)

  /**
   * Evaluates model output and returns a scalar metric.
   * The value of [[isLargerBetter]] specifies whether larger values are better.
   *
   * @param dataset a dataset that contains labels/observations and predictions.
   * @return metric
   */
  override def evaluate(dataset: Dataset[_]): Double = {null}

  @Since("1.5.0") override
  def copy(extra: ParamMap): Evaluator = {null}

  override val uid: String = _
}
