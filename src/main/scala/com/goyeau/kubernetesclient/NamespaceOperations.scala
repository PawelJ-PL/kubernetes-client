package com.goyeau.kubernetesclient

import akka.actor.ActorSystem
import akka.http.scaladsl.model.Uri
import io.circe._
import io.k8s.api.core.v1.{Namespace, NamespaceList}

private[kubernetesclient] case class NamespacesOperations(protected val config: KubeConfig)(
  implicit protected val system: ActorSystem,
  protected val resourceEncoder: Encoder[Namespace],
  decoder: Decoder[Namespace],
  protected val resourceDecoder: Decoder[NamespaceList]
) extends Creatable[Namespace]
    with CreateOrUpdatable[Namespace]
    with Listable[NamespaceList] {
  protected val resourceUri = s"${config.server}/api/v1/namespaces"

  def apply(namespace: String) = NamespaceOperations(config, s"$resourceUri/$namespace")
}

private[kubernetesclient] case class NamespaceOperations(protected val config: KubeConfig,
                                                         protected val resourceUri: Uri)(
  implicit protected val system: ActorSystem,
  protected val resourceEncoder: Encoder[Namespace],
  protected val resourceDecoder: Decoder[Namespace]
) extends Gettable[Namespace]
    with Replaceable[Namespace]
    with Deletable
