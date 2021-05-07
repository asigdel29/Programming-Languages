% starting points
% ending y-points
% size of th grid
% park goers
% created path

%possible moves
%move up
move([X,Y],[X2,Y2]):-
    X2 is X, Y2 is Y+1.
%move right
move([X,Y],[X2,Y2]):-
    X2 is X+1, Y2 is Y.
%move left
move([X,Y],[X2,Y2]):-
    X2 is X-1, Y2 is Y.

% graph limit
graph([GX,GY],[X2,Y2]):-
    X2>=0, %% x bound 0
    Y2>=0, %% y bound 0
    GX>=X2, %% x bound limit
    GY>=Y2.%%  y bound limit

% safe base case
safe([], _).

% safe recur
safe([[X2,Y2]|Locations], [NX,NY]) :-
   sqrt((X2-NX)^2 + (Y2-NY)^2) >= 6,
    safe(Locations,[NX,NY]).

solve([SX,SY], Ending, [MX, MY], Locations, Answer) :-
   path([SX,SY],Ending, [MX, MY], Locations, [],Answer).

%base
path([_,SY], Ending,_,_,List,Answer):-
     SY == Ending,
    append([],List,Answer).

%recur
path([SX,SY], Ending,[MX, MY],Locations,List,Answer):-
    SY \= Ending,
move([SX,SY],[X2,Y2]),
    graph([MX,MY],[X2,Y2]),
    safe(Locations,[X2,Y2]),
    \+member([X2,Y2],List),
    append(List,[[X2,Y2]],Newlist),
    path([X2,Y2],Ending,[MX, MY],Locations,Newlist,Answer).

