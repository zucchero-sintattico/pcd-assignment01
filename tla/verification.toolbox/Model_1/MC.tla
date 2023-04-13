---- MODULE MC ----
EXTENDS verification, TLC

\* CONSTANT definitions @modelParameterConstants:0MaxQueueSize
const_168137868233642000 == 
10
----

\* SPECIFICATION definition @modelBehaviorSpec:0
spec_168137868234643000 ==
Spec
----
\* INVARIANT definition @modelCorrectnessInvariants:0
inv_168137868235644000 ==
BoundedPathQueue
----
\* INVARIANT definition @modelCorrectnessInvariants:1
inv_168137868236745000 ==
BoundedStatsQueue
----
\* PROPERTY definition @modelCorrectnessProperties:0
prop_168137868237746000 ==
PathProducerWillEnd
----
\* PROPERTY definition @modelCorrectnessProperties:1
prop_168137868238847000 ==
StatsConsumerWillEnd
----
\* PROPERTY definition @modelCorrectnessProperties:2
prop_168137868239848000 ==
PathConsumersWillEnd
----
\* PROPERTY definition @modelCorrectnessProperties:3
prop_168137868240949000 ==
NoPushOnStatsQueueClosed
----
\* PROPERTY definition @modelCorrectnessProperties:4
prop_168137868241950000 ==
AllWillEnd
----
\* PROPERTY definition @modelCorrectnessProperties:5
prop_168137868242951000 ==
NoPushOnPathQueueClosed
----
=============================================================================
\* Modification History
\* Created Thu Apr 13 11:38:02 CEST 2023 by alessandro
