<template>

</template>



<script>
    import Manageframe from '../../components/manageframe'
    import popupwin from '../../components/popupwin.vue'
    export default {
        name: "search-account",
        data () {
            return {
                pagename: 'catagory',
                newCatID: '', // 新category的ID
                newCatName: '', // 新category的name
                button1: '<i class="zmdi zmdi-edit"></i>edit',
                account: this.$store.state.account,
                category: null,
                categoryList: null,
                productList: null,
                deleteCategoryList: [],
                checkVal: [],
                keyword: '',
                title: 'edit',
                isEdit: false, // 编辑模式
                isNew: false, // 新建模式
                show: false// 显示通知框
            }
        },
        components: {
            Manageframe,
            popupwin
        },
        methods: {
            // todo 改进建议 列表显示翻页
            hideModal () {
                // 取消弹窗回调
                this.canScroll()
                this.show = false
            },
            submit () {
                // 确认弹窗回调
                this.canScroll()
                this.show = false
            },
            openMask () {
                // 打开弹窗
                this.noScroll()
                this.show = true
            }, // 打开编辑弹窗
            editNewCategory () {
                this.isNew = !this.isNew
                this.isEdit = false// 添加新category时应该关闭编辑模式
            }, // 打开新增category编辑页面
            submitNewCategory () {
                let params = {
                    categoryId: this.newCatID,
                    name: this.newCatName
                }
                this.axios({
                    method: 'post',
                    url: '/management/categories',
                    data: params,
                    contentType: 'application/json'
                }).then(res => {
                    if (res.data.status) {
                        alert('已添加新种类,ID:' + this.newCatID + ',Name:' + this.newCatName)
                        this.categoryList.push(res.data.result.category)
                        this.isNew = false
                        this.newCatID = ''
                        this.newCatName = ''
                    } else {
                        alert('添加新种类失败,原因:' + res.data.msg)
                    }
                }).catch(err => {
                    console.log('请求服务器失败')
                })
            }, // 上传新category到后台
            deleteCategory () {
                console.log('即将删除:' + this.deleteCategoryList)
                if (this.deleteCategoryList.length > 0) {
                    let categoryIdList = this.deleteCategoryList
                    this.axios({
                        method: 'delete',
                        url: '/management/categories',
                        data: categoryIdList,
                        contentType: 'application/json'
                    }).then(res => {
                        if (res.data.status) {
                            alert('删除成功')
                            // 同步页面数据
                            for (let i = 0; i < this.categoryList.length; i++) {
                                // console.log(this.categoryList[i]+"   "+this.deleteCategoryList.indexOf(this.categoryList[i]));
                                if (this.deleteCategoryList.indexOf(this.categoryList[i].categoryId) !== -1) {
                                    this.categoryList.splice(i, 1);
                                    i = 0
                                }
                            }
                            this.deleteCategoryList = []
                            // this.isEdit = false;
                        } else {
                            alert('删除失败,原因' + res.data.msg)
                        }
                    }).catch(err => {
                        console.log('发生错误')
                    })
                }
            }, // 上传选中Category删除
            selectDelete (e) {
                let categoryId = e.currentTarget.id
                if (e.target.checked) {
                    this.deleteCategoryList.push(categoryId)
                } else {
                    for (let i = 0; i < this.deleteCategoryList.length; i++) {
                        if (this.deleteCategoryList[i] === categoryId) this.deleteCategoryList.splice(i, 1)
                    }
                }
                console.log('当前选中:' + this.deleteCategoryList)
            }, // 选中category的id加入List
            editCategory () {
                this.isEdit = !this.isEdit
                this.isNew = false
                if(this.isEdit === false) this.deleteCategoryList =[]
            }// 编辑模式来回切换
        },
        created () {
            this.axios.get('/management/accounts/results', {
                params: {
                    keyword: this.$route.query.keyword
                }
            }).then(res => {
                this.accountList = res.data.result.accountList
                console.log(this.accountList)
            })
        }
    }
</script>

<style scoped>

</style>
