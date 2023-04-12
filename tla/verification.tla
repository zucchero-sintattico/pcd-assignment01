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
  BoundedPathQueue == Len(pathqueue) >= 0 /\ Len(pathqueue) <= MaxQueueSize 
  BoundedStatsQueue == Len(statsqueue) >= 0 /\ Len(statsqueue) <= MaxQueueSize
  
  PathProducerWillEnd == <>(pc["pathproducer"] = "Done")
  PathConsumersWillEnd == <>(pc["pathconsumer1"] = "Done" /\ pc["pathconsumer2"] = "Done")
  StatsConsumerWillEnd == <>(pc["statsconsumer"] = "Done")
  AllWillEnd == <>(pc["pathproducer"] = "Done" /\ pc["pathconsumer1"] = "Done" /\ pc["pathconsumer2"] = "Done" /\ pc["statsconsumer"] = "Done")
  
  NoPushOnPathQueueClosed == [](pathqueueclosed ~> ~<>(pc["pathproducer"] = "put"))
  NoPushOnStatsQueueClosed == [](statsqueueclosed ~> ~<>(pc["pathconsumer1"] = "put" \/ pc["pathconsumer2"] = "put"))
end define;

fair process pathproducer \in { "pathproducer" } 
variables currentItem = "";
begin Produce:
  while TRUE do
    produce:
        if pathitems /= <<>> then
            currentItem := Head(pathitems);
            pathitems := Tail(pathitems);
            goto put;
        else
            goto closepathqueue;
        end if;
    put: 
        await Len(pathqueue) < MaxQueueSize;
        pathqueue := Append(pathqueue, currentItem);
  end while;
  closepathqueue:
    pathqueueclosed := TRUE;
end process;

fair process pathconsumer \in { "pathconsumer1", "pathconsumer2" }
variables pathitem = "none", statitem = "none";
begin Consume:
  while TRUE do
    take: 
        if ~pathqueueclosed /\ pathqueue /= <<>> then
                pathitem := Head(pathqueue);
                pathqueue := Tail(pathqueue);
        elsif ~pathqueueclosed /\ pathqueue = <<>> then
                await pathqueue /= <<>>;
                pathitem := Head(pathqueue);
                pathqueue := Tail(pathqueue);
        else
            goto closestatsqueue;
        end if;
    produce:
        statitem := "statitem";
    consume:
        await Len(statsqueue) < MaxQueueSize;
        statsqueue := Append(statsqueue, statitem);
  end while;
  closestatsqueue:
    statsqueueclosed := TRUE;
end process;

fair process statsconsumer \in { "statsconsumer" }
variable statitem = "none";
begin Consume:
  while TRUE do
    take: 
        if ~statsqueueclosed /\ statsqueue /= <<>> then
                statitem := Head(statsqueue);
                statsqueue := Tail(statsqueue);
        elsif ~statsqueueclosed /\ statsqueue = <<>> then
                await statsqueue /= <<>>;
                statitem := Head(statsqueue);
                statsqueue := Tail(statsqueue);
        else
            goto theend;
        end if;
  end while;
  theend:
    skip;
end process;


end algorithm;*)

\* BEGIN TRANSLATION (chksum(pcal) = "26c3172" /\ chksum(tla) = "e7cb84b")
\* Label produce of process pathproducer at line 32 col 9 changed to produce_
\* Label Consume of process pathconsumer at line 50 col 3 changed to Consume_
\* Label take of process pathconsumer at line 52 col 9 changed to take_
\* Process variable statitem of process pathconsumer at line 48 col 30 changed to statitem_
VARIABLES pathitems, pathqueue, pathqueueclosed, statsqueue, statsqueueclosed, 
          pc

(* define statement *)
BoundedPathQueue == Len(pathqueue) >= 0 /\ Len(pathqueue) <= MaxQueueSize
BoundedStatsQueue == Len(statsqueue) >= 0 /\ Len(statsqueue) <= MaxQueueSize

PathProducerWillEnd == <>(pc["pathproducer"] = "Done")
PathConsumersWillEnd == <>(pc["pathconsumer1"] = "Done" /\ pc["pathconsumer2"] = "Done")
StatsConsumerWillEnd == <>(pc["statsconsumer"] = "Done")
AllWillEnd == <>(pc["pathproducer"] = "Done" /\ pc["pathconsumer1"] = "Done" /\ pc["pathconsumer2"] = "Done" /\ pc["statsconsumer"] = "Done")

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
                 /\ pc' = [pc EXCEPT ![self] = "produce_"]
                 /\ UNCHANGED << pathitems, pathqueue, pathqueueclosed, 
                                 statsqueue, statsqueueclosed, currentItem, 
                                 pathitem, statitem_, statitem >>

produce_(self) == /\ pc[self] = "produce_"
                  /\ IF pathitems /= <<>>
                        THEN /\ currentItem' = [currentItem EXCEPT ![self] = Head(pathitems)]
                             /\ pathitems' = Tail(pathitems)
                             /\ pc' = [pc EXCEPT ![self] = "put"]
                        ELSE /\ pc' = [pc EXCEPT ![self] = "closepathqueue"]
                             /\ UNCHANGED << pathitems, currentItem >>
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
                  /\ pc' = [pc EXCEPT ![self] = "take_"]
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
                 /\ pc' = [pc EXCEPT ![self] = "take"]
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
\* Last modified Wed Apr 12 16:19:35 CEST 2023 by alessandro
\* Last modified Sun Mar 28 15:40:26 CEST 2021 by aricci
\* Created Sun Mar 28 08:34:06 CEST 2021 by aricci

=============================================================================
\* Modification History
\* Created Sun Mar 28 15:32:19 CEST 2021 by aricci
