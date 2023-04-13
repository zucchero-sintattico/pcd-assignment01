---- MODULE MC ----
EXTENDS verification, TLC

\* CONSTANT definitions @modelParameterConstants:0MaxQueueSize
const_1681381206981199000 == 
10
----

\* SPECIFICATION definition @modelBehaviorSpec:0
spec_1681381206992200000 ==
Spec
----
\* INVARIANT definition @modelCorrectnessInvariants:0
inv_1681381207002201000 ==
BoundedPathQueue
----
\* INVARIANT definition @modelCorrectnessInvariants:1
inv_1681381207013202000 ==
BoundedStatsQueue
----
\* PROPERTY definition @modelCorrectnessProperties:0
prop_1681381207023203000 ==
PathProducerWillEnd
----
\* PROPERTY definition @modelCorrectnessProperties:1
prop_1681381207033204000 ==
StatsConsumerWillEnd
----
\* PROPERTY definition @modelCorrectnessProperties:2
prop_1681381207044205000 ==
PathConsumersWillEnd
----
\* PROPERTY definition @modelCorrectnessProperties:3
prop_1681381207054206000 ==
NoPushOnStatsQueueClosed
----
\* PROPERTY definition @modelCorrectnessProperties:4
prop_1681381207065207000 ==
AllWillEnd
----
\* PROPERTY definition @modelCorrectnessProperties:5
prop_1681381207075208000 ==
NoPushOnPathQueueClosed
----
\* PROPERTY definition @modelCorrectnessProperties:6
prop_1681381207086209000 ==
PathQueueWillBeClosed
----
\* PROPERTY definition @modelCorrectnessProperties:7
prop_1681381207096210000 ==
StatsQueueWillBeClosed
----
=============================================================================
\* Modification History
\* Created Thu Apr 13 12:20:07 CEST 2023 by alessandro
