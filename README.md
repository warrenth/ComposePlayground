
### LazyColumn 의 key 란? (RecyclerView diffUtil 과 차이점)
- RecyclerView 는 DiffUtil 기반 비교, LazyColumn 은 key 기반의 비교 (key 가 없다면 index 기반 비교) <br>
- LazyColumn 에서 key 설정하지 않으면 index 기반 비교를 한다. 아이템 추가/삭제로 위치가 변경된다면 리컴포지션, 스크롤 깜빡임, 애니메이션 꼬임 발생한다.
#### 키를 만드는 방법은?
1. 고유 식별자가 있다면 <br>
```items(products, key = { it.productId }) { product -> ... }```
2. 교유 식별자가  없다면 <br>
```items(things, key = { it.hashCode() }) { thing -> ... }```
단점 : 값이 바뀌면 hashCode 값도 바뀜
3. index 사용 <br>
```items(things) { index, thing -> ... } // key 없음 = index 기준 비교```
단점 : 삽입, 삭제 시에 index 변경 되므로, 하단 리스트가 전부 recomposition 대상이 된다.

key 여부에 **recomposition counts, skipped counts** 차이 확인<br>
- skip 되는 것 : Composition / Layout / Drawing <br>
<img width="243" alt="Image" src="https://github.com/user-attachments/assets/b10c06c3-a906-4a59-99ec-2c6e1ffd01a1" />
<img width="236" alt="Image" src="https://github.com/user-attachments/assets/166cf7ef-72d9-4736-932c-4614a5688654" />



### LazyColumn 의 LayoutNode 최적화
> LayoutNode란?
> - LayoutNode는 Compose 내부에서 UI의 크기와 위치 정보를 관리하는 객체입니다.
> - 각 Composable은 Composition 과정에서 LayoutNode를 생성하며, 이 객체는 Layout 단계에서 사용됩니다.

#### LayoutNode 의 재활용
- 화면 밖으로 나가면 Composable은 Composition Tree에서 제거(drop)
- LayoutNode는 메모리에 남아서 재사용을 위한 캐시로 보관
- 화면에 들어올 때, 기존 LayoutNode를 재사용하여 measure() 단계 생략 가능
- Layout 구조나 contentType이 같다면, LayoutNode 재사용 + 재측정 생략됨

🟥 Text("Item 0")          ⟶ 🟩 LayoutNode 0 <br>
🟥 Text("Item 1")          ⟶ 🟩 LayoutNode 1 <br>
🟥 Text("Item 2")          ⟶ 🟩 LayoutNode 2 <br>
⬇️ 스크롤 <br>
🟦 Text("Item 3") 새로 등장 ⟶ 🟩 LayoutNode 0 (재사용!) <br>
🟦 Text("Item 4") 새로 등장 ⟶ 🟩 LayoutNode 1 (재사용!) <br>

### Compose vs	RecyclerView 
> Compose 
>- 빠른 UI 렌더링	key + contentType + remember	
>- 상태 보존	rememberSaveable
>- 다양한 UI 유형	contentType으로 구분 <br>

>RecyclerView
>- ViewHolder + DiffUtil
>- setTag(), Adapter state 저장
>- getItemViewType + ViewHolder subclassing

### Prefetching
>Compose 는 스크롤 방향을 예측해서 다음에 보여질 항목에 대해서 미리 만들어 놓는다.



https://developer.android.com/develop/ui/compose/tooling/debug?hl=ko
