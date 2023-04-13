--------------------------- MODULE verification ---------------------------

EXTENDS TLC, Integers, Sequences
CONSTANTS MaxQueueSize

(*--algorithm message_queue
variables pathitems = <<"el1", "el2", "el3">>,

          pathqueue = <<>>, 
          pathqueueclosed = FALSE, 
          
          statsqueue = <<>>, 
          statsqueueclosed = FALSE;
define
  \* Bounded Queue Checks
  BoundedPathQueue == Len(pathqueue) >= 0 /\ Len(pathqueue) <= MaxQueueSize 
  BoundedStatsQueue == Len(statsqueue) >= 0 /\ Len(statsqueue) <= MaxQueueSize
  
  \* Termination Checks
  PathProducerWillEnd == <>(pc["pathproducer"] = "Done")
  PathConsumersWillEnd == <>(pc["pathconsumer1"] = "Done" /\ pc["pathconsumer2"] = "Done")
  StatsConsumerWillEnd == <>(pc["statsconsumer"] = "Done")
  AllWillEnd == <>(pc["pathproducer"] = "Done" /\ pc["pathconsumer1"] = "Done" /\ pc["pathconsumer2"] = "Done" /\ pc["statsconsumer"] = "Done")
  
  \* Closed Queues Checks
  PathQueueWillBeClosed == <>pathqueueclosed
  StatsQueueWillBeClosed == <>statsqueueclosed
  
  \* No Push on closed Queues
  NoPushOnPathQueueClosed == [](pathqueueclosed ~> ~<>(pc["pathproducer"] = "put"))
  NoPushOnStatsQueueClosed == [](statsqueueclosed ~> ~<>(pc["pathconsumer1"] = "put" \/ pc["pathconsumer2"] = "put"))
end define;

macro push(q, e) begin
    q := Append(q, e);
end macro;

macro pop(q, e) begin
    e := Head(q);
    q := Tail(q);
end macro;

macro waitNotFull(q) begin
    await Len(q) < MaxQueueSize;
end macro;

macro waitNotEmpty(q) begin
    await q /= <<>>;
end macro;

macro waitNotFullAndPush(q, e) begin
    waitNotFull(q);
    push(q, e);
end macro;

macro waitNotEmptyAndPop(q, e) begin
    waitNotEmpty(q);
    pop(q, e);
end macro;

fair process pathproducer \in { "pathproducer" } 
variables currentItem = "";
begin Produce:
  while pathitems /= <<>> do
    produce:
        if pathitems = <<>> then
            goto closepathqueue;
        else
            pop(pathitems, currentItem);
        end if;
    put: 
        waitNotFullAndPush(pathqueue, currentItem);
  end while;
  closepathqueue:
    pathqueueclosed := TRUE;
end process;

fair process pathconsumer \in { "pathconsumer1", "pathconsumer2" }
variables pathitem = "none", statitem = "none";
begin Consume:
  while ~pathqueueclosed /\ pathqueue /= <<>> do
    take: 
        if ~pathqueueclosed /\ pathqueue /= <<>> then
            pop(pathqueue, pathitem);
        elsif ~pathqueueclosed /\ pathqueue = <<>> then
            waitNotEmptyAndPop(pathqueue, pathitem);
        else
            goto closestatsqueue;
        end if;
    produce:
        statitem := "statitem";
    consume:
        waitNotFullAndPush(statsqueue, statitem);
  end while;
  closestatsqueue:
    statsqueueclosed := TRUE;
end process;

fair process statsconsumer \in { "statsconsumer" }
variable statitem = "none";
begin Consume:
  while ~statsqueueclosed /\ statsqueue /= <<>> do
    take: 
        if ~statsqueueclosed /\ statsqueue /= <<>> then
            pop(statsqueue, statitem);
        elsif ~statsqueueclosed /\ statsqueue = <<>> then
            waitNotEmptyAndPop(statsqueue, statitem);
        else
            goto theend;
        end if;
  end while;
  theend:
    skip;
end process;


end algorithm;*)

\* BEGIN TRANSLATION (chksum(pcal) = "26c3172" /\ chksum(tla) = "e7cb84b")
\* Label produce of process pathproducer at line 66 col 9 changed to produce_
\* Label Consume of process pathconsumer at line 81 col 3 changed to Consume_
\* Label take of process pathconsumer at line 83 col 9 changed to take_
\* Process variable statitem of process pathconsumer at line 79 col 30 changed to statitem_
VARIABLES pathitems, pathqueue, pathqueueclosed, statsqueue, statsqueueclosed, 
          pc

(* define statement *)
BoundedPathQueue == Len(pathqueue) >= 0 /\ Len(pathqueue) <= MaxQueueSize
BoundedStatsQueue == Len(statsqueue) >= 0 /\ Len(statsqueue) <= MaxQueueSize


PathProducerWillEnd == <>(pc["pathproducer"] = "Done")
PathConsumersWillEnd == <>(pc["pathconsumer1"] = "Done" /\ pc["pathconsumer2"] = "Done")
StatsConsumerWillEnd == <>(pc["statsconsumer"] = "Done")
AllWillEnd == <>(pc["pathproducer"] = "Done" /\ pc["pathconsumer1"] = "Done" /\ pc["pathconsumer2"] = "Done" /\ pc["statsconsumer"] = "Done")


PathQueueWillBeClosed == <>pathqueueclosed
StatsQueueWillBeClosed == <>statsqueueclosed


NoPushOnPathQueueClosed == [](pathqueueclosed ~> ~<>(pc["pathproducer"] = "put"))
NoPushOnStatsQueueClosed == [](statsqueueclosed ~> ~<>(pc["pathconsumer1"] = "put" \/ pc["pathconsumer2"] = "put"))

VARIABLES currentItem, pathitem, statitem_, statitem

vars == << pathitems, pathqueue, pathqueueclosed, statsqueue, 
           statsqueueclosed, pc, currentItem, pathitem, statitem_, statitem
        >>

ProcSet == ({ "pathproducer" }) \cup ({ "pathconsumer1", "pathconsumer2" }) \cup ({ "statsconsumer" })

Init == (* Global variables *)
        /\ pathitems = <<"el1", "el2", "el3">>
        /\ pathqueue = <<>>
        /\ pathqueueclosed = FALSE
        /\ statsqueue = <<>>
        /\ statsqueueclosed = FALSE
        (* Process pathproducer *)
        /\ currentItem = [self \in { "pathproducer" } |-> ""]
        (* Process pathconsumer *)
        /\ pathitem = [self \in { "pathconsumer1", "pathconsumer2" } |-> "none"]
        /\ statitem_ = [self \in { "pathconsumer1", "pathconsumer2" } |-> "none"]
        (* Process statsconsumer *)
        /\ statitem = [self \in { "statsconsumer" } |-> "none"]
        /\ pc = [self \in ProcSet |-> CASE self \in { "pathproducer" } -> "Produce"
                                        [] self \in { "pathconsumer1", "pathconsumer2" } -> "Consume_"
                                        [] self \in { "statsconsumer" } -> "Consume"]

Produce(self) == /\ pc[self] = "Produce"
                 /\ IF pathitems /= <<>>
                       THEN /\ pc' = [pc EXCEPT ![self] = "produce_"]
                       ELSE /\ pc' = [pc EXCEPT ![self] = "closepathqueue"]
                 /\ UNCHANGED << pathitems, pathqueue, pathqueueclosed, 
                                 statsqueue, statsqueueclosed, currentItem, 
                                 pathitem, statitem_, statitem >>

produce_(self) == /\ pc[self] = "produce_"
                  /\ IF pathitems = <<>>
                        THEN /\ pc' = [pc EXCEPT ![self] = "closepathqueue"]
                             /\ UNCHANGED << pathitems, currentItem >>
                        ELSE /\ currentItem' = [currentItem EXCEPT ![self] = Head(pathitems)]
                             /\ pathitems' = Tail(pathitems)
                             /\ pc' = [pc EXCEPT ![self] = "put"]
                  /\ UNCHANGED << pathqueue, pathqueueclosed, statsqueue, 
                                  statsqueueclosed, pathitem, statitem_, 
                                  statitem >>

put(self) == /\ pc[self] = "put"
             /\ Len(pathqueue) < MaxQueueSize
             /\ pathqueue' = Append(pathqueue, currentItem[self])
             /\ pc' = [pc EXCEPT ![self] = "Produce"]
             /\ UNCHANGED << pathitems, pathqueueclosed, statsqueue, 
                             statsqueueclosed, currentItem, pathitem, 
                             statitem_, statitem >>

closepathqueue(self) == /\ pc[self] = "closepathqueue"
                        /\ pathqueueclosed' = TRUE
                        /\ pc' = [pc EXCEPT ![self] = "Done"]
                        /\ UNCHANGED << pathitems, pathqueue, statsqueue, 
                                        statsqueueclosed, currentItem, 
                                        pathitem, statitem_, statitem >>

pathproducer(self) == Produce(self) \/ produce_(self) \/ put(self)
                         \/ closepathqueue(self)

Consume_(self) == /\ pc[self] = "Consume_"
                  /\ IF ~pathqueueclosed /\ pathqueue /= <<>>
                        THEN /\ pc' = [pc EXCEPT ![self] = "take_"]
                        ELSE /\ pc' = [pc EXCEPT ![self] = "closestatsqueue"]
                  /\ UNCHANGED << pathitems, pathqueue, pathqueueclosed, 
                                  statsqueue, statsqueueclosed, currentItem, 
                                  pathitem, statitem_, statitem >>

take_(self) == /\ pc[self] = "take_"
               /\ IF ~pathqueueclosed /\ pathqueue /= <<>>
                     THEN /\ pathitem' = [pathitem EXCEPT ![self] = Head(pathqueue)]
                          /\ pathqueue' = Tail(pathqueue)
                          /\ pc' = [pc EXCEPT ![self] = "produce"]
                     ELSE /\ IF ~pathqueueclosed /\ pathqueue = <<>>
                                THEN /\ pathqueue /= <<>>
                                     /\ pathitem' = [pathitem EXCEPT ![self] = Head(pathqueue)]
                                     /\ pathqueue' = Tail(pathqueue)
                                     /\ pc' = [pc EXCEPT ![self] = "produce"]
                                ELSE /\ pc' = [pc EXCEPT ![self] = "closestatsqueue"]
                                     /\ UNCHANGED << pathqueue, pathitem >>
               /\ UNCHANGED << pathitems, pathqueueclosed, statsqueue, 
                               statsqueueclosed, currentItem, statitem_, 
                               statitem >>

produce(self) == /\ pc[self] = "produce"
                 /\ statitem_' = [statitem_ EXCEPT ![self] = "statitem"]
                 /\ pc' = [pc EXCEPT ![self] = "consume"]
                 /\ UNCHANGED << pathitems, pathqueue, pathqueueclosed, 
                                 statsqueue, statsqueueclosed, currentItem, 
                                 pathitem, statitem >>

consume(self) == /\ pc[self] = "consume"
                 /\ Len(statsqueue) < MaxQueueSize
                 /\ statsqueue' = Append(statsqueue, statitem_[self])
                 /\ pc' = [pc EXCEPT ![self] = "Consume_"]
                 /\ UNCHANGED << pathitems, pathqueue, pathqueueclosed, 
                                 statsqueueclosed, currentItem, pathitem, 
                                 statitem_, statitem >>

closestatsqueue(self) == /\ pc[self] = "closestatsqueue"
                         /\ statsqueueclosed' = TRUE
                         /\ pc' = [pc EXCEPT ![self] = "Done"]
                         /\ UNCHANGED << pathitems, pathqueue, pathqueueclosed, 
                                         statsqueue, currentItem, pathitem, 
                                         statitem_, statitem >>

pathconsumer(self) == Consume_(self) \/ take_(self) \/ produce(self)
                         \/ consume(self) \/ closestatsqueue(self)

Consume(self) == /\ pc[self] = "Consume"
                 /\ IF ~statsqueueclosed /\ statsqueue /= <<>>
                       THEN /\ pc' = [pc EXCEPT ![self] = "take"]
                       ELSE /\ pc' = [pc EXCEPT ![self] = "theend"]
                 /\ UNCHANGED << pathitems, pathqueue, pathqueueclosed, 
                                 statsqueue, statsqueueclosed, currentItem, 
                                 pathitem, statitem_, statitem >>

take(self) == /\ pc[self] = "take"
              /\ IF ~statsqueueclosed /\ statsqueue /= <<>>
                    THEN /\ statitem' = [statitem EXCEPT ![self] = Head(statsqueue)]
                         /\ statsqueue' = Tail(statsqueue)
                         /\ pc' = [pc EXCEPT ![self] = "Consume"]
                    ELSE /\ IF ~statsqueueclosed /\ statsqueue = <<>>
                               THEN /\ statsqueue /= <<>>
                                    /\ statitem' = [statitem EXCEPT ![self] = Head(statsqueue)]
                                    /\ statsqueue' = Tail(statsqueue)
                                    /\ pc' = [pc EXCEPT ![self] = "Consume"]
                               ELSE /\ pc' = [pc EXCEPT ![self] = "theend"]
                                    /\ UNCHANGED << statsqueue, statitem >>
              /\ UNCHANGED << pathitems, pathqueue, pathqueueclosed, 
                              statsqueueclosed, currentItem, pathitem, 
                              statitem_ >>

theend(self) == /\ pc[self] = "theend"
                /\ TRUE
                /\ pc' = [pc EXCEPT ![self] = "Done"]
                /\ UNCHANGED << pathitems, pathqueue, pathqueueclosed, 
                                statsqueue, statsqueueclosed, currentItem, 
                                pathitem, statitem_, statitem >>

statsconsumer(self) == Consume(self) \/ take(self) \/ theend(self)

Next == (\E self \in { "pathproducer" }: pathproducer(self))
           \/ (\E self \in { "pathconsumer1", "pathconsumer2" }: pathconsumer(self))
           \/ (\E self \in { "statsconsumer" }: statsconsumer(self))
           \/ (* Disjunct to prevent deadlock on termination *)
              ((\A self \in ProcSet: pc[self] = "Done") /\ UNCHANGED vars)

Spec == /\ Init /\ [][Next]_vars
        /\ \A self \in { "pathproducer" } : WF_vars(pathproducer(self))
        /\ \A self \in { "pathconsumer1", "pathconsumer2" } : WF_vars(pathconsumer(self))
        /\ \A self \in { "statsconsumer" } : WF_vars(statsconsumer(self))

Termination == <>(\A self \in ProcSet: pc[self] = "Done")

\* END TRANSLATION 

=============================================================================
\* Modification History
\* Last modified Thu Apr 13 12:22:32 CEST 2023 by alessandro
\* Last modified Sun Mar 28 15:40:26 CEST 2021 by aricci
\* Created Sun Mar 28 08:34:06 CEST 2021 by aricci

=============================================================================
\* Modification History
\* Created Sun Mar 28 15:32:19 CEST 2021 by aricci
