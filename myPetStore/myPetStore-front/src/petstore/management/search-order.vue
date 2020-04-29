<template>

</template>

<script>
    import Manageframe from '../../components/manageframe'
    import Modal from '../../components/popupwin.vue'
    export default {
        name: "search-order",
        data () {
            return {
                pagename: 'order',
                isSetDate: false,
                data: '日期',
                deleteOrderList: [],
                orderList: [],
                keyword: '',
                checkVal: [],
                title2: 'edit order',
                isEdit: false,
                show: false,
                filters: {
                    column: {
                        create_start_date: '',
                        create_end_date: ''
                    }
                },
                pickerBeginDateBefore: {
                    disabledDate: (time) => {
                        let beginDateVal = this.filters.column.create_end_date
                        let beginDateValx = this.filters.column.create_start_date
                        if (beginDateVal || beginDateValx) {
                            this.isSetDate = true
                        } else {
                            this.isSetDate = false
                        }
                        if (beginDateVal) {
                            return time.getTime() >= beginDateVal || time.getTime() >= Date.now()
                        } else {
                            return time.getTime() >= Date.now()
                        }
                    }
                },
                pickerBeginDateAfter: {
                    disabledDate: (time) => {
                        let beginDateValx = this.filters.column.create_end_date
                        let beginDateVal = this.filters.column.create_start_date
                        if (beginDateVal || beginDateValx) {
                            this.isSetDate = true
                        } else {
                            this.isSetDate = false
                        }
                        if (beginDateVal) {
                            return time.getTime() <= beginDateVal || time.getTime() >= Date.now()
                        } else {
                            return time.getTime() >= Date.now()
                        }
                    }
                }
            }
        },
        components: {
            Manageframe,
            Modal},
        methods: {
            isAccord (order) {
                let date = order.orderDate
                date = new Date(date)
                console.log(date)
                let beginDateValB = this.filters.column.create_start_date
                let beginDateValA = this.filters.column.create_end_date
                return ((date >= beginDateValB || beginDateValB === '') && (date <= beginDateValA || beginDateValA === '')) || !this.isSetDate
            },
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
                this.noScroll()
                this.show = true
            },
            addOrder () {
                alert('功能维护中...')// 待修改
            },
            deleteOrder () {
                // alert(this.checkVal)// 待添加方法
                if (this.deleteOrderList.length > 0) {
                    this.axios({
                        method: 'delete',
                        url: '/management/orders',
                        data: this.deleteOrderList,
                        contentType: 'application/json'
                    }).then(res => {
                        if (res.data.status) {
                            alert('删除成功')
                            // 同步前端数据
                            for (let i = 0; i < this.orderList.length; i++) {
                                if (this.deleteOrderList.indexOf('' + this.orderList[i].orderId) !== -1) {
                                    this.orderList.splice(i, 1)
                                    i = 0
                                }
                            }
                            this.deleteOrderList = []
                        } else {
                            alert('删除失败,原因:' + res.data.msg)
                        }
                    }).catch(err => {
                        alert('服务器错误')
                    })
                }
            }, // todo 上传选中的order删除
            selectDelete (e) {
                let orderId = e.currentTarget.id
                if (e.target.checked) {
                    this.deleteOrderList.push(orderId)
                } else {
                    for (let i = 0; i < this.deleteOrderList.length; i++) {
                        if (this.deleteOrderList[i] === orderId) this.deleteOrderList.splice(i, 1)
                    }
                }
                console.log('当前选中' + this.deleteOrderList)
            }, // 选中的order加入list
            editOrder () {
                this.isEdit = !this.isEdit
                if(this.isEdit === false) this.deleteOrderList =[]
            }, // 编辑模式来回切换
        },
        created () {
            console.log("key:"+this.$route.query.keyword);
            this.axios.get('/management/orders/results', {
                params: {
                    keyword: this.$route.query.keyword
                }
            }).then(res => {
                this.orderList = res.data.result.orderList
                console.log(this.orderList)
            })
        }
    }

</script>

<style scoped>

</style>
