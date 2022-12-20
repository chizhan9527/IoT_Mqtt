# coding=utf-8
import numpy as np
from scipy.optimize import curve_fit as curve_fit
# import keras
# from keras.layers import *
import json


# ------------------------------------------------------------------------------------------------------------------------------------
#   Logistic曲线拟合
#   函数forecast_by_Logistic(origin_data, target)
#   输入已有数据origin_data和目标预测天数target，返回后target天的预计数据
# ------------------------------------------------------------------------------------------------------------------------------------
# S型曲线函数公式定义
def func(x, k, a, b):
    return k / (1 + (k / b - 1) * np.exp(-a * x))


def forecast_by_Logistic(origin_data, target):
    n = len(origin_data)  # 自动计算上面输入的数据所对应的天数
    xdata = [i + 1 for i in range(n)]  # 横坐标数据，以第几天表示
    ydata = origin_data  # 纵坐标数据，表示每天对应的病例数

    # 非线性最小二乘法拟合
    popt, pcov = curve_fit(func, xdata, ydata, method='dogbox', bounds=([1000., 0.01, 10.], [10000000., 1.0, 1000.]))

    x = np.linspace(0, len(xdata) + target, len(xdata) + target + 1)  # 横坐标取值
    y = func(x, *popt)
    return y[len(xdata) + 1:]


# ------------------------------------------------------------------------------------------------------------------------------------
'''
#------------------------------------------------------------------------------------------------------------------------------------
#   堆叠LSTM算法
#   函数forecast_by_LSTM(origin_data, target)
#   输入已有数据origin_data和目标预测天数target，返回后target天的预计数据
#------------------------------------------------------------------------------------------------------------------------------------
# 堆叠LSTM 
def encoder_decoder_LSTM_train(n_input,n_out,n_features,epochs_num,train_data,train_label,vaild_data,vaild_label):
    model =keras.Sequential()
    model.add(LSTM(3, activation='tanh', return_sequences=True, input_shape=(n_input, n_features)))
    model.add(LSTM(3, activation='tanh', return_sequences=True, dropout=0.2, recurrent_dropout=0.2))
    model.add(LSTM(3, activation='tanh', dropout=0.2, recurrent_dropout=0.2))
    model.add(Dense(n_out))
    # 模型编译
    model.compile(optimizer='adam',loss='mse')
    model.fit(train_data,train_label,epochs=epochs_num,batch_size=None,shuffle=False,validation_data=(vaild_data,vaild_label))
    return model

# 根据输入数据个数，输出数据个数，步长，特征数，将原始的时间序列数据制作成为训练数据和标签；
def to_supervised(data, n_input, n_out, n_features,step):
    X, y = list(), list()
    in_start = 0 # in_start：当前训练数据的首位数据的位置。
    for _ in range(len(data)): # 遍历原始数据
        in_end = in_start + (n_input-1)*step # inend：当前训练数据的最后一位数据的位置
        out_end =in_end + step*n_out # out_end：当前标签的最后一位数据的位置
        if (out_end) <= len(data): #若作为标签的最后一位数据在原始数据的范围内，则可以划分一组训练数据和标签
            tmp_x = list()
            for i in range(n_input):
                if n_features == 1: # 当特征数为1，即只有单特征时间序列数据
                    element_x = list()
                    element_x.append(data[in_start + i * step]) #根据开始和结束位置存入训练数据（例如：[3,3,2,4,1]）
                    tmp_x.append(element_x) # 再存入容器中,这样与多特征的格式统一(格式变为：[[3,3,2,4,1]])
                else:
                    tmp_x.append(data[in_start+i*step,0:n_features]) # 多特征的时序数据，训练数据也存入多特征(例如3特征的时序数据：[[3,2,1],[4,7,5],[9,5,2]])
            X.append(tmp_x) # 再将该组时序数据的容器存入训练数据的容器
            y.append(data[in_end:in_end + step*n_out]) # 该组的标签存入标签容器中
        in_start += 1 # 更新起始位置
    return np.array(X),np.array(y)

def forecast_by_LSTM(origin_data, target):
    n_input = min(len(origin_data)/3,30)    # 输入的数据个数
    n_output = target    # 输出的数据个数
    n_features = 1  # 每个数据含有的特征数量
    step_lenth = 1  # 步长（时间序列的组合方式：若为1，表示1，2，3的方式组合；若为2，表示1，3，5的方式组合）
    epochs_num=500   # 训练的迭代次数
    # 载入数据集
    org_case_count = origin_data # 存储原始病例数的容器
    
    # # 归一化
    max_val = max(org_case_count) # 取最大值
    # print(max_val)
    case_count=list() # 存储归一化后病例数的容器
    for data in org_case_count:
        tmp = data/max_val # 将所有病例数据都控制在0~1之间
        case_count.append(tmp)  # 添加归一化后病例数
    
    # 制作训练集序列
    case_count = np.array(case_count) # 转换为numpy.array格式
    
    # 将数据制作成训练数据和标签
    case_data,case_label = to_supervised(case_count,n_input,n_output,n_features,step_lenth)
    print(case_data.shape)
    print(case_label.shape)
    
    # 划分训练集和验证集。这里表示将后三组的训练数据和标签划为验证集
    train_data = case_data[:-3]
    train_label = case_label[:-3]
    valid_data = case_data[-3:]
    valid_label = case_label[-3:]
       
    # 模型训练 
    model = encoder_decoder_LSTM_train(n_input, n_output, n_features, epochs_num,train_data, train_label, valid_data, valid_label)

    # 模型预测 
    # 取最后的n_input位作为
    org_test_data = case_count[-n_input:] 
    test_data = list()
    tmp = list()
    for data in org_test_data:
        element_x = list()
        element_x.append(data)
        tmp.append(element_x)
    test_data.append(tmp)
    test_data = np.array(test_data)

    y_hat = model.predict(test_data).reshape((-1))
    y_hat *= max_val

    return y_hat
#------------------------------------------------------------------------------------------------------------------------------------
'''
# ------------------------------------------------------------------------------------------------------------------------------------

# confront data
with open("IoT_Mqtt/src/main/java/com/project/iot_mqtt/python/data/conformData.json", "r") as fin:
    conformData = json.load(fin)
conform_data= conformData['conformData']
conform_target = conformData['target']
conform_forecast = list(forecast_by_Logistic(conform_data, conform_target))
fin.close()

with open("IoT_Mqtt/src/main/java/com/project/iot_mqtt/python/data/conform_forecast.json", 'w') as fout:
    json.dump({"conformForecast": json.dumps(conform_forecast)}, fout)
fout.close()

# cured data
with open("IoT_Mqtt/src/main/java/com/project/iot_mqtt/python/data/curedData.json", "r") as fin:
    curedData = json.load(fin)
cured_data= curedData['curedData']
cured_target = curedData['target']
cured_forecast = list(forecast_by_Logistic(cured_data, cured_target))
fin.close()

with open("IoT_Mqtt/src/main/java/com/project/iot_mqtt/python/data/cured_forecast.json", 'w') as fout:
    json.dump({"curedForecast": json.dumps(cured_forecast)}, fout)
fout.close()

# dead data
with open("IoT_Mqtt/src/main/java/com/project/iot_mqtt/python/data/deadData.json", "r") as fin:
    deadData = json.load(fin)
dead_data= deadData['deadData']
dead_target = deadData['target']
dead_forecast = list(forecast_by_Logistic(dead_data, dead_target))
fin.close()

with open("IoT_Mqtt/src/main/java/com/project/iot_mqtt/python/data/dead_forecast.json", 'w') as fout:
    json.dump({"deadForecast": json.dumps(dead_forecast)}, fout)
fout.close()