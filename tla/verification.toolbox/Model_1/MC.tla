---- MODULE MC ----
EXTENDS verification, TLC

\* CONSTANT definitions @modelParameterConstants:0MaxQueueSize
const_1681379251973102000 == 
10
----

\* SPECIFICATION definition @modelBehaviorSpec:0
spec_1681379251983103000 ==
Spec
----
\* INVARIANT definition @modelCorrectnessInvariants:0
inv_1681379251994104000 ==
BoundedPathQueue
----
\* INVARIANT definition @modelCorrectnessInvariants:1
inv_1681379252004105000 ==
BoundedStatsQueue
----
\* PROPERTY definition @modelCorrectnessProperties:0
prop_1681379252014106000 ==
PathProducerWillEnd
----
\* PROPERTY definition @modelCorrectnessProperties:1
prop_1681379252024107000 ==
StatsConsumerWillEnd
----
\* PROPERTY definition @modelCorrectnessProperties:2
prop_1681379252034108000 ==
PathConsumersWillEnd
----
\* PROPERTY definition @modelCorrectnessProperties:3
prop_1681379252044109000 ==
NoPushOnStatsQueueClosed
----
\* PROPERTY definition @modelCorrectnessProperties:4
prop_1681379252054110000 ==
AllWillEnd
----
\* PROPERTY definition @modelCorrectnessProperties:5
prop_1681379252064111000 ==
NoPushOnPathQueueClosed
----
=============================================================================
\* Modification History
\* Created Thu Apr 13 11:47:32 CEST 2023 by alessandro
