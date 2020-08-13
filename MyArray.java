package Array;

public class Myarray {
	// 数组中元素的数据类型，本例中是long类型的数组，
	// 本来想弄个泛型，结果不会搞，先就这样吧
	private long[] arr;
	// 数组中的元素数目，刚创建，啥都还没装呢，所以是0
	private int elementes = 0;
	// 数组的大小，用来扩容用的
	private int size = 0;
	// 用来扩容的数组
	private long[] arrgrow;
	// 左指针
	private int left;
	// 右指针
	private int right;

	/**
	 * 空参构造
	 */
	public Myarray() {
		// 默认数组长度为16
		arr = new long[16];
	}

	/**
	 * 有参构造，传入一个自定义数组的长度值
	 * 
	 * @param MaxSize
	 */
	public Myarray(int MaxSize) {
		// 数组的长度设置为用户输入的值
		arr = new long[MaxSize];
	}

	/**
	 * 添加数组元素，普通添加，就是添加到末尾
	 * 
	 * @param value
	 */
	public void add(long value) {
		// 添加的时候判断，当前的元素数目是否达到了数组长度的75%，如果没有则正常添加
		size = arr.length;
		if (elementes < arr.length * 0.75) {
			arr[elementes] = value;
			elementes++;
		} else {
			// 达到扩容条件了，开始扩容,这部分代码被我们提出去了，直接this调用即可
			arr = expansion();
			// 正常添加
			arr[elementes] = value;
			elementes++;
		}
	}

	/**
	 * 插入前排序
	 * 
	 * @param value
	 */
	public void orderadd(long value) {
		size = arr.length;
		// l 用于找到比要插入的数大的数的索引
		int l = 0;
		// 插入前还是判断是否扩容
		if (elementes < arr.length * 0.75) {
			orders(value, l);
		} else {
			// 达到扩容条件了，开始扩容,这部分代码被我们提出去了，直接this调用即可
			arr = expansion();
			// 扩容完毕，继续添加
			orders(value, l);
		}
	}

	/**
	 * 遍历格式化打印数组中的数据
	 */
	public void show() {
		// 当只有一个数据时，就直接打印，也别浪费时间去遍历了
		if (elementes == 1) {
			System.out.println("[" + arr[elementes - 1] + "]");
		} else {
			// 格式化打印成 [-1,0,1,3,4,5] 这种格式
			System.out.print("[");
			for (int i = 0; i < elementes - 1; i++) {
				System.out.print(arr[i] + ",");
			}
			System.out.print(arr[elementes - 1]);
			System.out.println("]");
		}
	}

	/**
	 * 在数组中查找数据，找到第一个后返回对应的索引值，没找到的话，返回-1
	 * 
	 * @param value
	 * @return
	 */
	public int searchByValue(long value) {
		int j;
		for (j = 0; j < arr.length; j++) {
			// 遍历数组，对比，找到相等的了就退出循环，这样就获得了对应的索引
			if (value == arr[j]) {
				break;
			}
		}
		// 如果找到了最后一个，则说明没有找到，返回-1
		if (j == elementes) {
			return -1;
		} else {
			// 找到了，返回对应的索引值
			return j;
		}
	}

	/**
	 * 通过二分法查找 二分法查找的前提是数组必须得是有序的
	 * 
	 * @param value
	 * @return
	 */
	public int binarySearch(long value) {
		// 三个位置，最左边的数，最右边的数，中间的数
		int middle = 0;
		int left = 0;
		int right = elementes - 1;
		while (true) {
			// 一直找，一直找
			// 计算中间的数等于两边数的和整除2
			middle = (left + right) / 2;
			// 判断中间数是否匹配
			if (arr[middle] == value) {
				// 如果中间的数匹配，则返回中间的数，这玩意儿就是你要找的数的索引
				return middle;
			} else if (left > right) {
				// 如果不是且左边的数都大于右边的数了，则说明没有找到，返回-1
				return -1;
			} else {
				// 剩下的情况
				if (arr[middle] > value) {
					// 如果中间的数据大于要找的数，则证明那个数在中间数的左边
					// 中间数-1变成右边数，继续找
					right = middle - 1;
				} else {
					// 如果中间的数据小于要找的数，则证明该数在中间数的右边
					// 中间数+1变为左边数，继续找
					left = middle + 1;
				}
			}
		}
	}

	/**
	 * 通过索引查找，输入索引，返回数值
	 * 
	 * @param index
	 * @return
	 */
	public long getByIndex(int index) {
		// 初始化返回值为-1,代表没有找到
		long result = -1;
		if (index < 0 || index >= arr.length) {
			// 如果用户输入的索引越界了，打印信息告知
			System.out.println("您输入的索引越界了，请检查后输入");
		} else {
			// 找到了，给返回值重新赋值为查找到的索引
			result = arr[index];
		}
		return result;
	}

	/**
	 * 通过索引删除
	 * 
	 * @param index
	 */
	public void deleteByIndex(int index) {
		if (index < 0 || index >= arr.length) {
			System.out.println("您输入的索引越界了，带脑子了嘛？");
		} else {
			for (int k = index; k < arr.length - 1; k++) {
				arr[k] = arr[k + 1];
			}
			arr[elementes - 1] = 0;
			elementes--;
		}

	}

	/**
	 * 冒泡排序法，当我们使用了第一个添加的方法，添加的数据是没有顺序的，这个时候如果想要使用方法中提供的
	 * 二分法查找是不可以的，可以先使用冒泡排序法将数组排序，然后再进行查找
	 */
	public void bubbleSort() {
		// 定义一个瓶子
		long tmp = 0;
		// 第一层循环，遍历整个数组
		for (int i = 0; i < elementes - 1; i++) {
			// 第二层循环，从后往前遍历，取得最小的数，一步步移动到前面
			for (int j = elementes - 1; j > i; j--) {
				if (arr[j] < arr[j - 1]) {
					tmp = arr[j];
					arr[j] = arr[j - 1];
					arr[j - 1] = tmp;
				}
			}
		}
	}

	/**
	 * 插入排序法
	 */
	public void insertSort() {
		// 定义一个瓶子
		long tmp = 0;
		// 从第二个数据开始遍历
		for (int i = 1; i < elementes; i++) {
			// 将要比较的数据装到瓶子里
			tmp = arr[i];
			// 从该位置开始比较
			int j = i;
			// 比较该位置的数字与前面的数字的大小，找到比该数小的数
			while (j > 0 && arr[j - 1] >= tmp) {
				// 将比它大的数字依次往后挪
				arr[j] = arr[j - 1];
				j--;
			}
			// 瓶子里面的数字比前面的大了，退出循环，将瓶子里面的数字插入那个数字的后面
			arr[j] = tmp;
		}
	}

	/**
	 * 选择排序
	 */
	public void selectionSort() {
		int k = 0;
		long tmp = 0;
		for (int i = 0; i <= elementes - 1; i++) {
			k = i;
			for (int j = i; j < elementes; j++) {
				if (arr[k] > arr[j]) {
					k = j;
				}
			}
			tmp = arr[i];
			arr[i] = arr[k];
			arr[k] = tmp;
		}
	}

	/**
	 * 希尔排序
	 */
	public void shellSort() {
		// 计算间隔，h初始值为1
		int h = 1;
		while (h < elementes / 3) {
			h = h * 3 + 1;
		}
		// 当间隔大于0时，进行排序
		while (h > 0) {
			long tmp = 0;
			// i从h开始，步长也为间隔h
			for (int i = h; i < elementes; i++) {
				// 将当前位置数据先装到瓶子里
				tmp = arr[i];
				// 从当前位置往前比对
				int j = i;
				// h最小为1, 当j大于h-0时，且当前数据大于瓶子里的数据时，交换
				while (j > h - 1 && arr[j - h] >= tmp) {
					arr[j] = arr[j - h];
					j -= h;
				}
				// 将数据插入
				arr[j] = tmp;
			}
			// 每循环一次，间隔数要减少，还是按照增加的方式反着来，这样可以保证h最终会等于1
			h = (h - 1) / 3;
		}
	}

	/**
	 * 前面的添加数据部分，用到了扩容的代码，这部分代码的重复性较高，所以将它抽取出来
	 * 
	 * @param size
	 */
	public long[] expansion() {
		// 这个扩容方法比较笨哈，就是新new一个长度为原来1.5倍的数组，然后把旧数组全装进去
		size = (int) (size * 1.5);
		arrgrow = new long[size];
		for (int j = 0; j < elementes; j++) {
			arrgrow[j] = arr[j];
		}
		// 将扩容后的数组指向arr
		arr = arrgrow;
		return arr;
	}

	/**
	 * 排序插入时重复代码抽出来的
	 * 
	 * @param value
	 * @param l
	 */
	public void orders(long value, int l) {
		// 挨个遍历数组，比对value和各个数的大小
		for (l = 0; l < elementes; l++) {
			// 这里这个elementes其实就是总共的元素数，插入了一个，就是+1
			// 如果有个数比value大，则证明value应该插入到 l 这个位置，退出循环
			if (value < arr[l]) {
				break;
			}
		}
		// 将 l 到最后一个数整体往后移动一位，以留出 l 的位置
		for (int m = elementes; m > l; m--) {
			// 倒着遍历，将l位到elementes的数据都往后移动一位
			arr[m] = arr[m - 1];
		}
		// 将数据插入到 l 的位置
		arr[l] = value;
		elementes++;
	}
	
	/**
	 * 将整个数组分区，分成以关键字为分界线，左边的都比关键字小，右边的都比关键字大
	 * @param left
	 * @param right
	 * @param key
	 * @return
	 */
	public int Partition(int left, int right, long key) {
		// 左指针
		int leftkey = left - 1;
		// 右指针
		int rightkey = right;
		// 死循环，除非达到跳出条件，否则，一直循环
		while (true) {
			// 左指针小于右指针，且指针所指小于关键字时，一直往右走
			while (leftkey < rightkey && arr[++leftkey] < key);
			// 右指针大于左指针，且指针所指大于关键字时，一直往左走
			while (leftkey < rightkey && arr[--rightkey] > key);
			// 如果两个指针重合了，或者左指针跑右指针的右边去了，退出循环
			if (leftkey >= rightkey) {
				break;
			} else {
				// 将左右两个指针所指的数字交换
				long tmp = arr[leftkey];
				arr[leftkey] = arr[rightkey];
				arr[rightkey] = tmp;
			}
		}
		// 跳出循环后，因为左指针这个时候肯定是大于等于关键字的，所以将左
		// 指针所指跟关键字交换，关键字就到了分界线部位 
		long bootle = arr[leftkey];
		arr[leftkey] = arr[right];
		arr[right] = bootle;
		return leftkey;
	}

	/**
	 * 快速排序主体，是一个递归算法，调用了自身
	 * @param left
	 * @param right
	 */
	public void quickSort_body(int left, int right) {
		// 如果子数组只有一个元素，就别费功夫了，直接return
		if(arr.length == 1) {
			return;
		}else {
			// 如果左指针大于等于右指针了，也直接return
			if(right - left <= 0) {
				return;
			}else {
				// 否则，每次取数组右端数字为关键字
				long key = arr[right];
				// 获取分区之后的分界线
				int partition = Partition(left, right, key);
				// 继续排序左端子数组
				quickSort_body(left, partition -1);
				// 继续排序右端子数组
				quickSort_body(partition+1, right);
			}
		}
	}
	
	/**
	 * 为了不传参数，只好这样了，加个套
	 */
	public void quickSort() {
		left = 0;
		right = elementes -1;
		quickSort_body(left, right);
	}
}
