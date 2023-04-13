---- MODULE MC ----
EXTENDS verification, TLC

\* CONSTANT definitions @modelParameterConstants:0MaxQueueSize
const_1681381355858211000 == 
10
----

\* SPECIFICATION definition @modelBehaviorSpec:0
spec_1681381355868212000 ==
Spec
----
\* INVARIANT definition @modelCorrectnessInvariants:0
inv_1681381355879213000 ==
BoundedPathQueue
----
\* INVARIANT definition @modelCorrectnessInvariants:1
inv_1681381355889214000 ==
BoundedStatsQueue
----
\* PROPERTY definition @modelCorrectnessProperties:0
prop_1681381355899215000 ==
PathProducerWillEnd
----
\* PROPERTY definition @modelCorrectnessProperties:1
prop_1681381355909216000 ==
StatsConsumerWillEnd
----
\* PROPERTY definition @modelCorrectnessProperties:2
prop_1681381355919217000 ==
PathConsumersWillEnd
----
\* PROPERTY definition @modelCorrectnessProperties:3
prop_1681381355929218000 ==
NoPushOnStatsQueueClosed
----
\* PROPERTY definition @modelCorrectnessProperties:4
prop_1681381355940219000 ==
AllWillEnd
----
\* PROPERTY definition @modelCorrectnessProperties:5
prop_1681381355951220000 ==
NoPushOnPathQueueClosed
----
\* PROPERTY definition @modelCorrectnessProperties:6
prop_1681381355961221000 ==
PathQueueWillBeClosed
----
\* PROPERTY definition @modelCorrectnessProperties:7
prop_1681381355972222000 ==
StatsQueueWillBeClosed
----
=============================================================================
\* Modification History
\* Created Thu Apr 13 12:22:35 CEST 2023 by alessandro
