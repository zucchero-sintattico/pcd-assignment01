---- MODULE MC ----
EXTENDS verification, TLC

\* CONSTANT definitions @modelParameterConstants:0MaxQueueSize
const_1681309181849301000 == 
10
----

\* SPECIFICATION definition @modelBehaviorSpec:0
spec_1681309181859302000 ==
Spec
----
\* INVARIANT definition @modelCorrectnessInvariants:0
inv_1681309181870303000 ==
BoundedPathQueue
----
\* INVARIANT definition @modelCorrectnessInvariants:1
inv_1681309181880304000 ==
BoundedStatsQueue
----
\* PROPERTY definition @modelCorrectnessProperties:0
prop_1681309181890305000 ==
PathProducerWillEnd
----
\* PROPERTY definition @modelCorrectnessProperties:1
prop_1681309181900306000 ==
StatsConsumerWillEnd
----
\* PROPERTY definition @modelCorrectnessProperties:2
prop_1681309181910307000 ==
PathConsumersWillEnd
----
\* PROPERTY definition @modelCorrectnessProperties:3
prop_1681309181920308000 ==
NoPushOnStatsQueueClosed
----
\* PROPERTY definition @modelCorrectnessProperties:4
prop_1681309181930309000 ==
AllWillEnd
----
\* PROPERTY definition @modelCorrectnessProperties:5
prop_1681309181940310000 ==
NoPushOnPathQueueClosed
----
=============================================================================
\* Modification History
\* Created Wed Apr 12 16:19:41 CEST 2023 by alessandro
